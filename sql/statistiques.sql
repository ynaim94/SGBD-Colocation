-- Version minimal ( les 2 premieres requetes)
-- Version nominale ( les 3 premieres requetes)
-- Version avancée ( les 4 requetes)

-- La liste des colocation avec le nombre de leurs membre à une date donnée

select C.NOM_COLOCATION AS COLOCATION,CM.NOM_CONTRAT_MEMBRE AS NOM_DU_MEMBRE,CM.PRENOM_CONTRAT_MEMBRE AS PRENOM_DU_MEMBRE
from COLOCATION C,CONTRAT_MEMBRE CM
where C.ID_COLOCATION=CM.ID_COLOCATION
      and CM.DATE_ENTRE<XX
      and CM.DATE_SORTIE>XX;


-- Pour chaque achat, le nombre de personne concernées
-- Pour une personne donnée, la liste des débit et des crédits avec leur montant. une personne a un crédit si elle effectue un achat ou verse de l'argent à une autre personne ou à une cagnotte. Elle a un debit si elle recoit de l'argent ou si elle est concernée par un achat ( dans ce cas, le dbit est égal au montant de l'achat divisé par le nombre de personne concernées)
-- Pour une colocation, la liste de ses membre avec leur solde.
