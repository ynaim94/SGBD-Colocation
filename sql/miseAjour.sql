-- Ajout, suppression, modification d’une personne, d’une colocation ; ajout d’un achat, d’un versement, etc. Toute autre requête que vous jugeriez utile pour la gestion de cette base. Au niveau du fonctionnement de l’application, on pourra supposer que lors de la création d’une colocation dans la base, on pourra choisir le mode de modification "ouvert" ou "fermé" ; en mode ouvert, chaque membre peut enregistrer des achats ou versement, en mode fermé, seul le gestionnaire peut enregistrer des achats qui concernent la colocation ou des versements sur la cagnotte ; dans tous les cas, seul le gestionnaire peut inscrire et désinscrire une personne de la colocation.


create sequence seq_personne
minvalue 1
start with 1
increment by 1
cache 10;
