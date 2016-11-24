-- ============================================================
--   Nom de la base   :  COLOCATION                                
--   Nom de SGBD      :  ORACLE Version 7.0                    
--   Date de creation :  21/11/2016
-- ============================================================

drop table COLOCATION cascade constraints;

drop table PERSONNE cascade constraints;

drop table BENEFICIAIRE cascade constraints;

drop table ACHAT_PERSONNEL cascade constraints;

drop table ACHAT_COLOCATION cascade constraints;

drop table ABONDEMENT cascade constraints;

drop table CONTRAT_MEMBRE cascade constraints;

drop table VERSEMENT cascade constraints;
-- ============================================================
--   Table : COLOCATION                                            
-- ============================================================
create table COLOCATION
(
    ID_COLOCATION                   NUMBER(3)              not null,
    ADRESSE_COLOCATION              CHAR(20)               not null,
    NOM_COLOCATION                  CHAR(20)               not null,
    A_UNE_CAGNOTTE                  BOOLEAN                not null,
    ID_PERSONNE                     NUMBER(3)              not null,
    constraint pk_colocation primary key (ID_COLOCATION)
);

-- ============================================================
--   Table : PERSONNE                                       
-- ============================================================
create table PERSONNE
(
    ID_PERSONNE                     NUMBER(3)              not null,
    NOM_PERSONNE                    CHAR(20)               not null,
    PRENOM_PERSONNE                 CHAR(20)               not null,
    MAIL_PERSONNE                   CHAR(20)               not null,
    constraint pk_personne primary key (ID_PERSONNE)
);


-- ============================================================
--   Table : ACHAT_COLOCATION                                              
-- ============================================================
create table ACHAT_COLOCATION
(
    ID_ACHAT_COLOCATION             NUMBER(3)              not null,
    INTITULE_ACHAT_COLOCATION       CHAR(30)               not null,
    DATE_ACHAT_COLOCATION           DATE                   not null,
    MONTANT_ACHAT_COLOCATION        INT                    not null,
    ID_COLOCATION                   NUMBER(3)              not null,
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    constraint pk_achat_colocation primary key (ID_ACHAT_COLOCATION)
);

-- ============================================================
--   Table : ABONDEMENT                                              
-- ============================================================
create table ABONDEMENT
(
    ID_ABONDEMENT                   NUMBER(3)              not null,
    DATE_ABONDEMENT                 DATE                   not null,	
    MONTANT_ABONDEMENT              INT                    not null,
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    constraint pk_abondement primary key (ID_ABONDEMENT)
);


-- ============================================================
--   Table : VERSEMENT                                              
-- ============================================================
create table VERSEMENT
(
    ID_VERSEMENT                    NUMBER(3)              not null,
    DATE_VERSEMENT                  DATE                   not null,	
    MONTANT_VERSEMENT               INT                    not null,
    ID_CONTRAT_MEMBRE_PAYEUR        NUMBER(3)              not null,
    ID_CONTRAT_MEMBRE_RECEVEUR      NUMBER(3)              not null,
    constraint pk_versement primary key (ID_VERSEMENT)
);


-- ============================================================
--   Table : CONTRAT_MEMBRE
-- ============================================================
create table CONTRAT_MEMBRE
(
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    DATE_ENTREE                     DATE                   not null,	
    DATE_SORTIE                     DATE                   not null,	
    ID_COLOCATION                   NUMBER(3)              not null,
    ID_PERSONNE                     NUMBER(3)              not null,
    constraint pk_versement primary key (ID_VERSEMENT)
);


-- ============================================================
--   Table : ACHAT_PERSONNEL                                              
-- ============================================================
create table ACHAT_PERSONNEL
(
    ID_ACHAT_PERSONNEL              NUMBER(3)              not null,
    INTITULE_ACHAT_PERSONNEL        CHAR(30)               not null,
    DATE_ACHAT_PERSONNEL            DATE                   not null,
    MONTANT_ACHAT_PERSONNEL         INT                    not null,
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    constraint pk_achat_personnel primary key (ID_ACHAT_PERSONNEL)
);

-- ============================================================
--   Table : BENEFICIAIRE                                              
-- ============================================================
create table BENEFICIAIRE
(
    ID_BENEFICIAIRE                 NUMBER(3)              not null,
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    ID_ACHAT_PERSONNEL              NUMBER(3)              not null,
    constraint pk_beneficiaire primary key (ID_BENEFICIAIRE)
);

alter table COLOCATION
    add constraint fk1_colocation foreign key (ID_PERSONNE)
       references PERSONNE (ID_PERSONNE);

alter table ACHAT_COLOCATION
    add constraint fk1_achat_colocation foreign key (ID_COLOCATION)
       references COLOCATION (ID_COLOCATION);

alter table ACHAT_COLOCATION
    add constraint fk2_achat_colocation foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE);

alter table ABONDEMENT
    add constraint fk1_abondement foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE);

alter table VERSEMENT
    add constraint fk1_versement foreign key (ID_CONTRAT_MEMBRE_PAYEUR)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE);

alter table VERSEMENT
    add constraint fk2_versement foreign key (ID_CONTRAT_MEMBRE_RECEVEUR)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE);

alter table CONTRAT_MEMBRE
    add constraint fk1_contrat_membre foreign key (ID_COLOCATION)
       references COLOCATION (ID_COLOCATION);

alter table CONTRAT_MEMBRE
    add constraint fk2_contrat_membre foreign key (ID_PERSONNE)
       references PERSONNE (ID_PERSONNE);

alter table ACHAT_PERSONNEL
    add constraint fk1_achat_personnel foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE);

alter table BENEFICIAIRE
    add constraint fk1_beneficiare foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE);

alter table BENEFICIAIRE
    add constraint fk2_beneficiare foreign key (ID_ACHAT_PEROSNNEL)
       references ACHAT_PERSONNEL (ID_ACHAT_PERSONNEL);
