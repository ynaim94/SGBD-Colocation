-- ajouté une personne a une colocation (= crée un contrat member)

insert into CONTRAT_MEMBREV
values (seq_contrat_membre.nextval, ?, ?, ?, ?);
