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
    ID_COLOCATION                   NUMBER(3)              NOT NULL,
    ADRESSE_COLOCATION              CHAR(40)               not null,
    NOM_COLOCATION                  CHAR(40)               not null,
    A_UNE_CAGNOTTE                  CHAR(1)                not null,
    ID_PERSONNE                     NUMBER(3)              not null,
    constraint pk_colocation primary key (ID_COLOCATION)
);


drop sequence seq_colocation;

create sequence seq_colocation
minvalue 10
start with 10
increment by 1
cache 10;


-- ============================================================
--   Table : PERSONNE                                       
-- ============================================================
create table PERSONNE
(
    ID_PERSONNE                     NUMBER(3)              not null,
    NOM_PERSONNE                    CHAR(40)               not null,
    PRENOM_PERSONNE                 CHAR(40)               not null,
    MAIL_PERSONNE                   CHAR(40)               not null,
    constraint pk_personne primary key (ID_PERSONNE)
);

drop sequence seq_personne;

create sequence seq_personne
minvalue 10
start with 10
increment by 1
cache 10;

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
    ID_CONTRAT_MEMBRE               NUMBER(3)              ,
    constraint pk_achat_colocation primary key (ID_ACHAT_COLOCATION)
);

drop sequence seq_achat_colocation;

create sequence seq_achat_colocation
minvalue 10
start with 10
increment by 1
cache 10;

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

drop sequence seq_abondement;

create sequence seq_abondement
minvalue 10
start with 10
increment by 1
cache 10;

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

drop sequence seq_versement;

create sequence seq_versement
minvalue 10
start with 10
increment by 1
cache 10;

-- ============================================================
--   Table : CONTRAT_MEMBRE
-- ============================================================
create table CONTRAT_MEMBRE
(
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    DATE_ENTREE                     DATE                   not null,	
    DATE_SORTIE                     DATE                   ,	
    ID_COLOCATION                   NUMBER(3)              not null,
    ID_PERSONNE                     NUMBER(3)              not null,
    constraint pk_contrat_membre primary key (ID_CONTRAT_MEMBRE)
);

drop sequence seq_contrat_membre;

create sequence seq_contrat_membre
minvalue 10
start with 10
increment by 1
cache 10;

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

drop sequence seq_achat_personnel;

create sequence seq_achat_personnel
minvalue 10
start with 10
increment by 1
cache 10;

-- ============================================================
--   Table : BENEFICIAIRE                                              
-- ============================================================
create table BENEFICIAIRE
(
    ID_CONTRAT_MEMBRE               NUMBER(3)              not null,
    ID_ACHAT_PERSONNEL              NUMBER(3)              not null,
    constraint pk_beneficiaire primary key (ID_CONTRAT_MEMBRE, ID_ACHAT_PERSONNEL)
);

alter table COLOCATION
    add constraint fk1_colocation foreign key (ID_PERSONNE)
       references PERSONNE (ID_PERSONNE) on delete cascade;

alter table ACHAT_COLOCATION
    add constraint fk1_achat_colocation foreign key (ID_COLOCATION)
       references COLOCATION (ID_COLOCATION) on delete cascade;

alter table ACHAT_COLOCATION
    add constraint fk2_achat_colocation foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE) on delete cascade;

alter table ABONDEMENT
    add constraint fk1_abondement foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE) on delete cascade;

alter table VERSEMENT
    add constraint fk1_versement foreign key (ID_CONTRAT_MEMBRE_PAYEUR)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE) on delete cascade;

alter table VERSEMENT
    add constraint fk2_versement foreign key (ID_CONTRAT_MEMBRE_RECEVEUR)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE) on delete cascade;

alter table CONTRAT_MEMBRE
    add constraint fk1_contrat_membre foreign key (ID_COLOCATION)
       references COLOCATION (ID_COLOCATION) on delete cascade;

alter table CONTRAT_MEMBRE
    add constraint fk2_contrat_membre foreign key (ID_PERSONNE)
       references PERSONNE (ID_PERSONNE) on delete cascade;

alter table ACHAT_PERSONNEL
    add constraint fk1_achat_personnel foreign key (ID_CONTRAT_MEMBRE)
       references CONTRAT_MEMBRE (ID_CONTRAT_MEMBRE) on delete cascade;


drop view CONTRAT_MEMBREV cascade constraints;
create view CONTRAT_MEMBREV as select * from CONTRAT_MEMBRE;

create trigger ins_contrat_membre
instead of insert on CONTRAT_MEMBREV
for each row
begin
	if :new.DATE_ENTREE <= :new.DATE_SORTIE
	then
		insert into CONTRAT_MEMBRE values (:new.ID_CONTRAT_MEMBRE, :new.DATE_ENTREE, :new.DATE_SORTIE, :new.ID_COLOCATION, :new.ID_PERSONNE);
	else
		null;
	end if;
end;
/
show error trigger ins_contrat_membre;


-- ============================================================
--   Vue pour les requêtes de consultation
-- ============================================================

drop view ACHAT_RECENT cascade constraints;
create view ACHAT_RECENT AS 
select COLOCATION.NOM_COLOCATION, COUNT(ACHAT_COLOCATION.INTITULE_ACHAT_COLOCATION) AS QUANTITE
from COLOCATION  left join ACHAT_COLOCATION  using (ID_COLOCATION)		  
where ACHAT_COLOCATION.DATE_ACHAT_COLOCATION > ADD_MONTHS(sysdate,-6)
group by COLOCATION.NOM_COLOCATION; 


-- ============================================================
--   Vue pour les requêtes de statistique
-- ============================================================

drop view perso_contrat cascade constraints;
create view perso_contrat (ID_CONTRAT_MEMBRE, ID_PERSONNE, ID_COLOCATION, DATE_ENTREE, DATE_SORTIE) as
select ID_CONTRAT_MEMBRE, ID_PERSONNE, ID_COLOCATION, DATE_ENTREE, DATE_SORTIE
from PERSONNE inner join CONTRAT_MEMBRE using (ID_PERSONNE);

drop view nbr_personne_achat cascade constraints;
create view nbr_personne_achat (ID_ACHAT, NOMBRE_DE_PERSONNE) as
select ID_ACHAT_PERSONNEL, count(ID_PERSONNE)
from (select ID_ACHAT_PERSONNEL, ID_PERSONNE
     from ACHAT_PERSONNEL left join (perso_contrat inner join BENEFICIAIRE using (ID_CONTRAT_MEMBRE)) using (ID_ACHAT_PERSONNEL)
     group by ID_ACHAT_PERSONNEL, ID_PERSONNE)
group by ID_ACHAT_PERSONNEL;
