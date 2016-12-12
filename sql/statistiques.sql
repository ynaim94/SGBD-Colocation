-- Version minimal ( les 2 premieres requetes)
-- Version nominale ( les 3 premieres requetes)
-- Version avancée ( les 4 requetes)

-- La liste des colocation avec le nombre de leurs membre à une date donnée

select COLOCATION.ID_COLOCATION, COLOCATION.NOM_COLOCATION, count(COLOCATION.ID_COLOCATION)
from COLOCATION, CONTRAT_MEMBRE
where COLOCATION.ID_COLOCATION = CONTRAT_MEMBRE.ID_COLOCATION
group by (COLOCATION.ID_COLOCATION, COLOCATION.NOM_COLOCATION);


-- Pour chaque achat, le nombre de personne concernées
drop view nbr_personne_achat bcascade constraints;
create view nbr_personne_achat (ID_ACHAT, NOMBRE_DE_PERSONNE) as
select ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL, count(ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL)
from ACHAT_PERSONNEL, BENEFICIAIRE
where ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL = BENEFICIAIRE.ID_ACHAT_PERSONNEL
group by ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL;

select * from nbr_personne_achat
where ID_ACHAT = 1;

select ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL, count(ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL)
from ACHAT_PERSONNEL, BENEFICIAIRE
where ACHAT_PERSONNEL.ID_ACHAT_PERSONNEL = BENEFICIAIRE.ID_ACHAT_PERSONNEL;

-- Pour une personne donnée, la liste des débit et des crédits avec leur montant. une personne a un crédit si elle effectue un achat ou verse de l'argent à une autre personne ou à une cagnotte. Elle a un debit si elle recoit de l'argent ou si elle est concernée par un achat ( dans ce cas, le débit est égal au montant de l'achat divisé par le nombre de personne concernées)
-- Pour une colocation, la liste de ses membre avec leur solde.
