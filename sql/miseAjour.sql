-- Ajout, suppression, modification d’une personne, d’une colocation ; ajout d’un achat, d’un versement, etc. Toute autre requête que vous jugeriez utile pour la gestion de cette base. Au niveau du fonctionnement de l’application, on pourra supposer que lors de la création d’une colocation dans la base, on pourra choisir le mode de modification "ouvert" ou "fermé" ; en mode ouvert, chaque membre peut enregistrer des achats ou versement, en mode fermé, seul le gestionnaire peut enregistrer des achats qui concernent la colocation ou des versements sur la cagnotte ; dans tous les cas, seul le gestionnaire peut inscrire et désinscrire une personne de la colocation.

-- donné test
insert into PERSONNE
values (42, 'NOM', 'PRENOM', 'MAIL');
insert into COLOCATION
values (666, 'ADRESSE', 'NOM', 'Y', 42);
insert into CONTRAT_MEMBRE
values (42, '01-JAN-08', '02-JAN-09', 666, 42);
insert into CONTRAT_MEMBRE
values (666, '01-JAN-08', '02-JAN-09', 666, 42);
insert into CONTRAT_MEMBRE
values (13, '01-JAN-08', '02-JAN-09', 666, 42);

-- ajouté une personne
insert into PERSONNE
values (seq_personne.nextval, 'NOM', 'PRENOM', 'MAIL');

-- ajouté une colocation
-- soit la personne 42 qui serat le gérantr de la futur colocation
insert into COLOCATION
values (seq_colocation.nextval, 'ADRESSE', 'NOM', 'Y', 42);

-- ajouté une personne a une colocation (= crée un contrat member)
-- soit la personne 42 et la colocation 666
insert into CONTRAT_MEMBRE
values (seq_contrat_membre.nextval, '01-JAN-08', '02-JAN-09', 666, 42);

-- ajouté un achat_personnel
-- soit le contrat membre  42 qui fait l'achat et 13 et 666 qui en bénificie
insert into ACHAT_PERSONNEL
values (seq_achat_personnel.nextval, 'INTITULEE', '01-JAN-08', 500, 42);

insert into BENEFICIAIRE
values (13, seq_achat_personnel.currval);

insert into BENEFICIAIRE
values (666, seq_achat_personnel.currval);

-- ajouté un achat_colocation
-- soit le contrat membre  42 qui fait l'achat et la colocation 666 qui en bénificie
insert into ACHAT_COLOCATION
values (seq_colocation.nextval, 'INTITULE', '01-JAN-08', 500, 666, 42);

insert into ACHAT_COLOCATION
values (seq_colocation.nextval, 'INTITULE', '01-JAN-08', 500, 666, null);
