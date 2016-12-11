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
insert into PERSONNE values (  6  , 'Duval' 	 , 'Loic'    	, 'LoicDuval@gmail.com' 	) ;
insert into PERSONNE values (  7  , 'Grandin' 	 , 'Eric'    	, 'EricGrandin@gmail.com' 	) ;

commit ;


-- COLOCATION (id_col, adr, nom, cagnotte, id_pers)

insert into COLOCATION values (  1  , '63 rue des Coudriers'	  ,  'A'  ,  'Y' ,  3  ) ;
insert into COLOCATION values (  2  , '15 Place du Jeu de Paume'  ,  'B'  ,  'Y' ,  5  ) ;

commit ;

-- CONTRAT_MEMBRE (id_contrat, date entree, date sortie, id coloc, id pers)

insert into CONTRAT_MEMBRE values (  1  ,  '01-JAN-2015'  , '01-JAN-2016' ,  1  ,  1  ) ;
insert into CONTRAT_MEMBRE values (  2  ,  '27-JUN-2015'  , null	  ,  1  ,  2  ) ;
insert into CONTRAT_MEMBRE values (  3  ,  '30-AUG-2015'  , null	  ,  1  ,  4  ) ;
insert into CONTRAT_MEMBRE values (  4  ,  '21-DEC-2015'  , null	  ,  2  ,  6  ) ;
insert into CONTRAT_MEMBRE values (  5  ,  '08-AUG-2015'  , null	  ,  2  ,  7  ) ;

commit ;

-- VERSEMENT (id_vers, date, montant, id_contrat_mb_payeur, id_contrat_membre_receveur)

insert into VERSEMENT values ( 1 , '10-NOV-2015' , 25 , 1 , 3 ) ;

commit ;

-- ABONDEMENT (id, date, montant, id_contrat_mb)

insert into ABONDEMENT values ( 1 , '01-AUG-2015' , 50 , 2 ) ;

commit ;


-- BENEFICIAIRE (id_contrat_mb, id achat_pers)

insert into BENEFICIAIRE values ( 2 , 2 ) ;
insert into BENEFICIAIRE values ( 3 , 2 ) ;

commit ;


--ACHAT_COLOCATION (id, intitulé, date, montant, id_coloc, id_contrat_mb)

insert into ACHAT_COLOCATION values ( 1 , 'Television' , '10-JAN-2015' , 500 , 1 , 1 ) ;
insert into ACHAT_COLOCATION values ( 2 , 'Frigo'      , '30-JUN-2015' , 399 , 1 , 2 ) ;
insert into ACHAT_COLOCATION values ( 3 , 'Table'      , '10-JAN-2016' , 125 , 2 , 4 ) ;
insert into ACHAT_COLOCATION values ( 4 , 'Canape'     , '10-SEP-2015' , 170 , 2 , 5 ) ;

commit ;


-- ACHAT_PERSONNEL (id, intitulé, date, montant, id_contrat_membre)

insert into ACHAT_PERSONNEL values ( 1 , 'Lit' 	 , '30-JUN-2015' , 199 , 2 ) ;
insert into ACHAT_PERSONNEL values ( 2 , 'Lampe' , '30-SEP-2015' , 29  , 1 ) ;

commit ;
