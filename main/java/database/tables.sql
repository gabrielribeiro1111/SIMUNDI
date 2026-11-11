
/* ============================================================
   SISTEMA DE GERENCIAMENTO DE ESTOQUE / GALPAO / ARMAZEM
   SGBD: SQL SERVER

   Modelo relacional consolidado
   ============================================================ */


/* ============================================================
   1. CLIENTE
   ============================================================ */

CREATE TABLE CLIENTE (
    COD_CLIENTE VARCHAR(30) NOT NULL,
    NOME NVARCHAR(150) NOT NULL,
    TIPO VARCHAR(2) NOT NULL,
    DOCUMENTO VARCHAR(20) NOT NULL,
    CONTATO NVARCHAR(255) NULL,
    INATIVO BIT NOT NULL DEFAULT 0,
    BLOQUEADO BIT NOT NULL DEFAULT 0,

    CONSTRAINT PK_CLIENTE
        PRIMARY KEY (COD_CLIENTE),

    CONSTRAINT CK_CLIENTE_TIPO
        CHECK (TIPO IN ('PF', 'PJ'))
);


/* ============================================================
   2. PRODUTO
   Cadastro interno de produtos controlados pela empresa.
   SKU nao e FK obrigatoria na ORDEM_SERVICO_ITEM.
   ============================================================ */

CREATE TABLE PRODUTO (
    SKU VARCHAR(50) NOT NULL,
    COD_BARRAS VARCHAR(50) NULL,
    DESC_PRODUTO NVARCHAR(255) NOT NULL,
    TIPO_PRODUTO VARCHAR(15) NOT NULL,
    NIVEL_AREA_VERMELHA INT NULL,
    NIVEL_CRITICO INT NULL,
    UNIDADE VARCHAR(20) NOT NULL,

    CONSTRAINT PK_PRODUTO
        PRIMARY KEY (SKU),

    CONSTRAINT CK_PRODUTO_TIPO
        CHECK (TIPO_PRODUTO IN (
            'PERECIVEL',
            'CONSUMIVEL',
            'PADRAO'
        )),

    CONSTRAINT CK_PRODUTO_NIVEIS
        CHECK (
            (NIVEL_AREA_VERMELHA IS NULL OR NIVEL_AREA_VERMELHA >= 0)
            AND
            (NIVEL_CRITICO IS NULL OR NIVEL_CRITICO >= 0)
        )
);


/* ============================================================
   3. TIPO DE ESTOQUE
   Representa os tipos de estruturas de armazenamento:
   pallet, caixa, container, freezer etc.
   ============================================================ */

CREATE TABLE TIPO_ESTOQUE (
    COD_TIPO_ESTOQUE INT IDENTITY(1,1) NOT NULL,
    DESC_TIPO_ESTOQUE NVARCHAR(150) NOT NULL,
    UNIDADE_ESTOQUE VARCHAR(20) NOT NULL,

    CONSTRAINT PK_TIPO_ESTOQUE
        PRIMARY KEY (COD_TIPO_ESTOQUE),

    CONSTRAINT UQ_TIPO_ESTOQUE_DESC
        UNIQUE (DESC_TIPO_ESTOQUE)
);


/* ============================================================
   4. TIPOS DE PRODUTOS PERMITIDOS POR TIPO DE ESTOQUE
   Relacionamento N:N entre TIPO_ESTOQUE e classificacao.
   ============================================================ */

CREATE TABLE TIPO_ESTOQUE_PRODUTO (
    COD_TIPO_ESTOQUE INT NOT NULL,
    TIPO_PRODUTO VARCHAR(15) NOT NULL,

    CONSTRAINT PK_TIPO_ESTOQUE_PRODUTO
        PRIMARY KEY (COD_TIPO_ESTOQUE, TIPO_PRODUTO),

    CONSTRAINT FK_TIPO_ESTOQUE_PRODUTO_TIPO
        FOREIGN KEY (COD_TIPO_ESTOQUE)
        REFERENCES TIPO_ESTOQUE (COD_TIPO_ESTOQUE),

    CONSTRAINT CK_TIPO_ESTOQUE_PRODUTO_TIPO
        CHECK (TIPO_PRODUTO IN (
            'PERECIVEL',
            'CONSUMIVEL',
            'PADRAO'
        ))
);


/* ============================================================
   5. ESTOQUE REAL
   Representa uma posicao fisica do armazem.

   VIRTUAL = 0: posicao fisica com coordenadas.
   VIRTUAL = 1: local logico/especial, como recebimento
                ou expedicao.
   ============================================================ */

CREATE TABLE ESTOQUE_REAL (
    ID INT IDENTITY(1,1) NOT NULL,
    IDENTIFICACAO NVARCHAR(100) NOT NULL,

    POS_X_INICIAL INT NULL,
    POS_Y_INICIAL INT NULL,
    POS_Z_INICIAL INT NULL,

    POS_X_FINAL INT NULL,
    POS_Y_FINAL INT NULL,
    POS_Z_FINAL INT NULL,

    COD_TIPO_ESTOQUE INT NOT NULL,
    ARMAZENAMENTO_MAX DECIMAL(18,4) NOT NULL,
    VIRTUAL BIT NOT NULL DEFAULT 0,
    ATIVA BIT NOT NULL DEFAULT 1,

    CONSTRAINT PK_ESTOQUE_REAL
        PRIMARY KEY (ID),

    CONSTRAINT FK_ESTOQUE_REAL_TIPO
        FOREIGN KEY (COD_TIPO_ESTOQUE)
        REFERENCES TIPO_ESTOQUE (COD_TIPO_ESTOQUE),

    CONSTRAINT CK_ESTOQUE_REAL_CAPACIDADE
        CHECK (ARMAZENAMENTO_MAX > 0),

    CONSTRAINT CK_ESTOQUE_REAL_COORDENADAS
        CHECK (
            (
                VIRTUAL = 1
                AND POS_X_INICIAL IS NULL
                AND POS_Y_INICIAL IS NULL
                AND POS_Z_INICIAL IS NULL
                AND POS_X_FINAL IS NULL
                AND POS_Y_FINAL IS NULL
                AND POS_Z_FINAL IS NULL
            )
            OR
            (
                VIRTUAL = 0
                AND POS_X_INICIAL IS NOT NULL
                AND POS_Y_INICIAL IS NOT NULL
                AND POS_Z_INICIAL IS NOT NULL
                AND POS_X_FINAL IS NOT NULL
                AND POS_Y_FINAL IS NOT NULL
                AND POS_Z_FINAL IS NOT NULL
                AND POS_X_FINAL >= POS_X_INICIAL
                AND POS_Y_FINAL >= POS_Y_INICIAL
                AND POS_Z_FINAL >= POS_Z_INICIAL
            )
        )
);


/* ============================================================
   6. ORDEM DE SERVICO
   ============================================================ */

CREATE TABLE ORDEM_SERVICO (
    ORDEM_SERVICO INT IDENTITY(1,1) NOT NULL,
    TIPO VARCHAR(10) NOT NULL,
    COD_CLIENTE VARCHAR(30) NOT NULL,
    EMISSAO DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    DURACAO INT NULL,
    STATUS VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    OBS NVARCHAR(MAX) NULL,

    CONSTRAINT PK_ORDEM_SERVICO
        PRIMARY KEY (ORDEM_SERVICO),

    CONSTRAINT FK_ORDEM_SERVICO_CLIENTE
        FOREIGN KEY (COD_CLIENTE)
        REFERENCES CLIENTE (COD_CLIENTE),

    CONSTRAINT CK_ORDEM_SERVICO_TIPO
        CHECK (TIPO IN ('ENTRADA', 'SAIDA')),

    CONSTRAINT CK_ORDEM_SERVICO_STATUS
        CHECK (STATUS IN (
            'PENDENTE',
            'EM_ANDAMENTO',
            'CONCLUIDA',
            'CANCELADA'
        )),

    CONSTRAINT CK_ORDEM_SERVICO_DURACAO
        CHECK (DURACAO IS NULL OR DURACAO >= 0)
);


/* ============================================================
   7. ORDEM DE SERVICO ITEM

   SKU nao possui FK para PRODUTO.
   O item pode representar produtos externos ou nao cadastrados.

   DESC_PRODUTO e UNIDADE preservam os dados originais
   recebidos no momento da operacao.
   ============================================================ */

CREATE TABLE ORDEM_SERVICO_ITEM (
    ID INT IDENTITY(1,1) NOT NULL,
    ORDEM_SERVICO INT NOT NULL,
    SKU VARCHAR(50) NULL,
    DESC_PRODUTO NVARCHAR(255) NOT NULL,
    COD_BARRAS VARCHAR(50) NULL,
    QTDE DECIMAL(18,4) NOT NULL,
    UNIDADE VARCHAR(20) NOT NULL,
    TIPO VARCHAR(15) NOT NULL,

    CONSTRAINT PK_ORDEM_SERVICO_ITEM
        PRIMARY KEY (ID),

    CONSTRAINT FK_ORDEM_SERVICO_ITEM_OS
        FOREIGN KEY (ORDEM_SERVICO)
        REFERENCES ORDEM_SERVICO (ORDEM_SERVICO),

    CONSTRAINT CK_ORDEM_SERVICO_ITEM_TIPO
        CHECK (TIPO IN (
            'PERECIVEL',
            'CONSUMIVEL',
            'PADRAO'
        )),

    CONSTRAINT CK_ORDEM_SERVICO_ITEM_QTDE
        CHECK (QTDE > 0)
);


/* ============================================================
   8. CONTROLE DE VALIDADE

   Cada registro representa um lote de um item perecivel
   associado a uma ordem de servico.
   ============================================================ */

CREATE TABLE CONTROLE_VALIDADE (
    ID INT IDENTITY(1,1) NOT NULL,
    ID_ITEM INT NOT NULL,
    LOTE VARCHAR(100) NOT NULL,
    VALIDADE DATE NOT NULL,
    QUANTIDADE DECIMAL(18,4) NOT NULL,

    CONSTRAINT PK_CONTROLE_VALIDADE
        PRIMARY KEY (ID),

    CONSTRAINT FK_CONTROLE_VALIDADE_ITEM
        FOREIGN KEY (ID_ITEM)
        REFERENCES ORDEM_SERVICO_ITEM (ID),

    CONSTRAINT CK_CONTROLE_VALIDADE_QUANTIDADE
        CHECK (QUANTIDADE > 0)
);


/* ============================================================
   9. MOVIMENTACAO DE ESTOQUE

   Registra a origem e o destino de cada movimentacao.

   Exemplos:
   - Entrada: RECEBIMENTO -> POSICAO FISICA
   - Saida: POSICAO FISICA -> EXPEDICAO
   - Transferencia interna: POSICAO A -> POSICAO B

   Os locais especiais devem ser cadastrados em ESTOQUE_REAL
   com VIRTUAL = 1.
   ============================================================ */

CREATE TABLE MOVIMENTACAO_ESTOQUE (
    ID INT IDENTITY(1,1) NOT NULL,
    ID_ITEM INT NOT NULL,
    ID_CONTROLE_VALIDADE INT NULL,
    ID_ESTOQUE_ORIGEM INT NOT NULL,
    ID_ESTOQUE_DESTINO INT NOT NULL,
    QUANTIDADE DECIMAL(18,4) NOT NULL,
    DATA_HORA DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT PK_MOVIMENTACAO_ESTOQUE
        PRIMARY KEY (ID),

    CONSTRAINT FK_MOVIMENTACAO_ESTOQUE_ITEM
        FOREIGN KEY (ID_ITEM)
        REFERENCES ORDEM_SERVICO_ITEM (ID),

    CONSTRAINT FK_MOVIMENTACAO_ESTOQUE_VALIDADE
        FOREIGN KEY (ID_CONTROLE_VALIDADE)
        REFERENCES CONTROLE_VALIDADE (ID),

    CONSTRAINT FK_MOVIMENTACAO_ESTOQUE_ORIGEM
        FOREIGN KEY (ID_ESTOQUE_ORIGEM)
        REFERENCES ESTOQUE_REAL (ID),

    CONSTRAINT FK_MOVIMENTACAO_ESTOQUE_DESTINO
        FOREIGN KEY (ID_ESTOQUE_DESTINO)
        REFERENCES ESTOQUE_REAL (ID),

    CONSTRAINT CK_MOVIMENTACAO_ESTOQUE_QUANTIDADE
        CHECK (QUANTIDADE > 0),

    CONSTRAINT CK_MOVIMENTACAO_ESTOQUE_POSICOES
        CHECK (ID_ESTOQUE_ORIGEM <> ID_ESTOQUE_DESTINO)
);


/* ============================================================
   10. INDICES
   ============================================================ */

CREATE INDEX IX_ORDEM_SERVICO_CLIENTE
    ON ORDEM_SERVICO (COD_CLIENTE);

CREATE INDEX IX_ORDEM_SERVICO_ITEM_OS
    ON ORDEM_SERVICO_ITEM (ORDEM_SERVICO);

CREATE INDEX IX_ORDEM_SERVICO_ITEM_SKU
    ON ORDEM_SERVICO_ITEM (SKU);

CREATE INDEX IX_ORDEM_SERVICO_ITEM_COD_BARRAS
    ON ORDEM_SERVICO_ITEM (COD_BARRAS);

CREATE INDEX IX_CONTROLE_VALIDADE_ITEM
    ON CONTROLE_VALIDADE (ID_ITEM);

CREATE INDEX IX_CONTROLE_VALIDADE_DATA
    ON CONTROLE_VALIDADE (VALIDADE);

CREATE INDEX IX_ESTOQUE_REAL_TIPO
    ON ESTOQUE_REAL (COD_TIPO_ESTOQUE);

CREATE INDEX IX_MOVIMENTACAO_ESTOQUE_ITEM
    ON MOVIMENTACAO_ESTOQUE (ID_ITEM);

CREATE INDEX IX_MOVIMENTACAO_ESTOQUE_VALIDADE
    ON MOVIMENTACAO_ESTOQUE (ID_CONTROLE_VALIDADE);

CREATE INDEX IX_MOVIMENTACAO_ESTOQUE_ORIGEM
    ON MOVIMENTACAO_ESTOQUE (ID_ESTOQUE_ORIGEM);

CREATE INDEX IX_MOVIMENTACAO_ESTOQUE_DESTINO
    ON MOVIMENTACAO_ESTOQUE (ID_ESTOQUE_DESTINO);

CREATE INDEX IX_MOVIMENTACAO_ESTOQUE_DATA
    ON MOVIMENTACAO_ESTOQUE (DATA_HORA);


/* ============================================================
   FIM DO SCRIPT
   ============================================================ */
