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

-- PERSONNE (id_per, nom, prenom, mail)

insert into PERSONNE values (  1  , 'Doyon'	 , 'Belle'	, 'BelleDoyon@gmail.com'	) ;
insert into PERSONNE values (  2  , 'Quiron'	 , 'Honore'   	, 'HonoreQuiron@gmail.com' 	) ;
insert into PERSONNE values (  3  , 'Hachee'	 , 'Stephane' 	, 'StephaneHachee@gmail.com' 	) ;
insert into PERSONNE values (  4  , 'Guibord' 	 , 'Bellamy'  	, 'BellamyGuibord@gmail.com' 	) ;
insert into PERSONNE values (  5  , 'Bertrand' 	 , 'Marc'    	, 'MarcBertrand@gmail.com' 	) ;

commit ;


-- COLOCATION (id_col, adr, nom, cagnotte, id_pers)

insert into COLOCATION values (  1  , '63 rue des Coudriers'	  ,  'A'  ,  'Y' ,  3  ) ;
insert into COLOCATION values (  2  , '15 Place du Jeu de Paume'  ,  'B'  ,  'Y' ,  5  ) ;
insert into COLOCATION values (  3  , '29 rue des Lacs'   	  ,  'C'  ,  'Y' ,  2  ) ;
insert into COLOCATION values (  4  , '36 rue des Soeurs'	  ,  'D'  ,  'Y' ,  1  ) ;
insert into COLOCATION values (  5  , '37 rue Ernest Renan'	  ,  'E'  ,  'Y' ,  4  ) ;


commit ;

-- CONTRAT_MEMBRE (id_contrat, date entree, date sortie, id coloc, id pers)

insert into CONTRAT_MEMBRE values (  1  ,  '01-JAN-2015'  , '01-JAN-2016' ,  1  ,  1  ) ;
insert into CONTRAT_MEMBRE values (  2  ,  '27-JUN-2015'  , null	  ,  1  ,  2  ) ;
insert into CONTRAT_MEMBRE values (  3  ,  '04-AUG-2015'  , null	  ,  2  ,  3  ) ;
insert into CONTRAT_MEMBRE values (  4  ,  '30-AUG-2015'  , null	  ,  2  ,  4  ) ;
insert into CONTRAT_MEMBRE values (  5  ,  '21-DEC-2015'  , null	  ,  2  ,  5  ) ;

commit ;
/*
-- VERSEMENT (id_vers, date, montant, id_contrat_mb_payeur, id_contrat_membre_receveur)

insert into VERSEMENT values ( , , , , ) ;
insert into VERSEMENT values ( , , , , ) ;
insert into VERSEMENT values ( , , , , ) ;
insert into VERSEMENT values ( , , , , ) ;

commit ;

-- ABONDEMENT (id, date, montant, id_contrat_mb)

insert into ABONDEMENT values ( , , , , ) ;
insert into ABONDEMENT values ( , , , , ) ;
insert into ABONDEMENT values ( , , , , ) ;
insert into ABONDEMENT values ( , , , , ) ;

commit ;

-- BENEFICIAIRE (id_contrat_mb, id beneficiaire)

insert into BENEFICIAIRE values ( , , , , ) ;
insert into BENEFICIAIRE values ( , , , , ) ;
insert into BENEFICIAIRE values ( , , , , ) ;
insert into BENEFICIAIRE values ( , , , , ) ;

commit ;

-- ACHAT_COLOCATION (id, intitulé, date, montant, id_coloc, id_contrat_mb)

insert into ACHAT_COLOCATION values ( , , , , ) ;
insert into ACHAT_COLOCATION values ( , , , , ) ;
insert into ACHAT_COLOCATION values ( , , , , ) ;
insert into ACHAT_COLOCATION values ( , , , , ) ;

commit ;

-- ACHAT_PERSONNEL (id, intitulé, date, montant, id_contrat_membre)

insert into ACHAT_PERSONNEL values ( , , , , ) ;
insert into ACHAT_PERSONNEL values ( , , , , ) ;
insert into ACHAT_PERSONNEL values ( , , , , ) ;
insert into ACHAT_PERSONNEL values ( , , , , ) ;

commit ;

*/
