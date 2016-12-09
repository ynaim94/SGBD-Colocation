-- Version minimal ( les 2 premieres requetes)
-- Version nominale ( les 3 premieres requetes)
-- Version avancée ( les 4 requetes)

-- La liste des colocation avec le nombre de leurs membre à une date donnée

select C.NOM_COLOCATION AS COLOCATION, P.NOM_PERSONNE AS NOM_DU_MEMBRE, P.PRENOM_PERSONNE AS PRENOM_DU_MEMBRE
from COLOCATION C,CONTRAT_MEMBRE CM, PERSONNE P
where C.ID_COLOCATION=CM.ID_COLOCATION
      and P.ID_PERSONNE=CM.ID_PERSONNE
      and CM.DATE_ENTREE<'08-AUG-2015'
      and (CM.DATE_SORTIE>'08-AUG-2015' OR CM.DATE_SORTIE=NULL);


-- Pour chaque achat, le nombre de personne concernées
-- Pour une personne donnée, la liste des débit et des crédits avec leur montant. une personne a un crédit si elle effectue un achat ou verse de l'argent à une autre personne ou à une cagnotte. Elle a un debit si elle recoit de l'argent ou si elle est concernée par un achat ( dans ce cas, le débit est égal au montant de l'achat divisé par le nombre de personne concernées)
-- Pour une colocation, la liste de ses membre avec leur solde.
