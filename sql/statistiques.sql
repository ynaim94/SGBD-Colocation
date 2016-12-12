@base
@donnees
-- Version minimal ( les 2 premieres requetes)
-- Version nominale ( les 3 premieres requetes)
-- Version avancée ( les 4 requetes)

-- La liste des colocation avec le nombre de leurs membre à une date donnée

select ID_COLOCATION, COLOCATION.NOM_COLOCATION, count(ID_CONTRAT_MEMBRE)
from COLOCATION left join CONTRAT_MEMBRE using (ID_COLOCATION)
group by (ID_COLOCATION, COLOCATION.NOM_COLOCATION);


-- Pour chaque achat, le nombre de personne concernées

select * from nbr_personne_achat
where ID_ACHAT = 1;
select * from nbr_personne_achat
where ID_ACHAT = 2;

-- Pour une personne donnée, la liste des débit et des crédits avec leur montant. une personne a un crédit si elle effectue un achat ou verse de l'argent à une autre personne ou à une cagnotte. Elle a un debit si elle recoit de l'argent ou si elle est concernée par un achat ( dans ce cas, le débit est égal au montant de l'achat divisé par le nombre de personne concernées)


-- Pour une colocation, la liste de ses membre avec leur solde.
