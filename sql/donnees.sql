-- ============================================================
--    suppression des donnees
-- ============================================================

delete from COLOCATION ;
delete from PERSONNE ;
delete from CONTRAT_MEMBRE ;
delete from VERSEMENT ;
delete from ABONDEMENT ;
delete from BENEFICIAIRE ;
delete from ACHAT_COLOCATION ;
delete from ACHAT_PERSONNEL ;

commit ;

-- ============================================================
--    creation des donnees
-- ============================================================

-- COLOCATION

insert into COLOCATION values ( , , , ) ;
insert into COLOCATION values ( , , , ) ;
insert into COLOCATION values ( , , , ) ;
insert into COLOCATION values ( , , , ) ;


commit ;

-- PERSONNE

insert into PERSONNE values ( , , , , ) ;
insert into PERSONNE values ( , , , , ) ;
insert into PERSONNE values ( , , , , ) ;
insert into PERSONNE values ( , , , , ) ;

commit ;

-- CONTRAT_MEMBRE

insert into CONTRAT_MEMBRE values ( , , , , ) ;
insert into CONTRAT_MEMBRE values ( , , , , ) ;
insert into CONTRAT_MEMBRE values ( , , , , ) ;
insert into CONTRAT_MEMBRE values ( , , , , ) ;

commit ;

-- VERSEMENT

insert into VERSEMENT values ( , , , , ) ;
insert into VERSEMENT values ( , , , , ) ;
insert into VERSEMENT values ( , , , , ) ;
insert into VERSEMENT values ( , , , , ) ;

commit ;

-- ABONDEMENT

insert into ABONDEMENT values ( , , , , ) ;
insert into ABONDEMENT values ( , , , , ) ;
insert into ABONDEMENT values ( , , , , ) ;
insert into ABONDEMENT values ( , , , , ) ;

commit ;

-- BENEFICIAIRE

insert into BENEFICIAIRE values ( , , , , ) ;
insert into BENEFICIAIRE values ( , , , , ) ;
insert into BENEFICIAIRE values ( , , , , ) ;
insert into BENEFICIAIRE values ( , , , , ) ;

commit ;

-- ACHAT_COLOCATION

insert into ACHAT_COLOCATION values ( , , , , ) ;
insert into ACHAT_COLOCATION values ( , , , , ) ;
insert into ACHAT_COLOCATION values ( , , , , ) ;
insert into ACHAT_COLOCATION values ( , , , , ) ;

commit ;

-- ACHAT_PERSONNEL

insert into ACHAT_PERSONNEL values ( , , , , ) ;
insert into ACHAT_PERSONNEL values ( , , , , ) ;
insert into ACHAT_PERSONNEL values ( , , , , ) ;
insert into ACHAT_PERSONNEL values ( , , , , ) ;

commit ;

-- ============================================================
--    verification des donnees
-- ============================================================

select count(*),'= 37 ?','ACTEUR' from ACTEUR 
union
select count(*),'= 20 ?','FILM' from FILM 
union
select count(*),'= 14 ?','REALISATEUR' from REALISATEUR 
union
select count(*),'= 40 ?','ROLE' from ROLE ;
