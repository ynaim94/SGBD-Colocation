@base

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

--------------------------------------------------------------------------------
-- ajouté une personne
insert into PERSONNE
values (seq_personne.nextval, 'NOM', 'PRENOM', 'MAIL');

--------------------------------------------------------------------------------
-- ajouté une colocation
-- soit la personne 42 qui serat le gérantr de la futur colocation
insert into COLOCATION
values (seq_colocation.nextval, 'ADRESSE', 'NOM', 'Y', 42);

--------------------------------------------------------------------------------
-- ajouté une personne a une colocation (= crée un contrat member)
-- soit la personne 42 et la colocation 666

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

insert into CONTRAT_MEMBREV
values (seq_contrat_membre.nextval, '01-DEC-08', '02-DEC-09', 666, 42);

insert into CONTRAT_MEMBREV
values (seq_contrat_membre.nextval, '09-DEC-09', '04-DEC-09', 666, 42);

select * from CONTRAT_MEMBRE;



