# PARANOÏA Social — Android starter

Projet Android natif Kotlin pour la première interface du réseau social PARANOÏA (thème noir et violet).

## Contenu actuel
- Accueil et navigation à cinq onglets
- Écrans de démonstration : Découvrir, Publier, Vidéos, Actualités, Rencontres, Messages, Notifications, Profil et Paramètres
- Boutons et navigation interactifs
- Workflow GitHub Actions pour générer un APK de débogage

## Limites importantes
Cette version est un **prototype d'interface**, pas encore un réseau social connecté. Les contenus visibles sont des exemples. Aucun compte réel, publication en ligne, téléchargement média, chat réel, système de rencontres, actualité en direct ni notification push n'est encore connecté. Ces fonctions nécessiteront une backend (par exemple Supabase), des règles de sécurité, des tests et des paramètres de confidentialité.

## Générer l'APK via GitHub
1. Créez un dépôt privé nommé `paranoia-social`.
2. Téléversez tous les fichiers et dossiers de ce projet en conservant la structure, notamment `.github/workflows/build-apk.yml`.
3. Ouvrez l'onglet **Actions**, sélectionnez **Build Paranoia Social APK**, puis **Run workflow** si la compilation ne démarre pas automatiquement.
4. Une fois la compilation terminée avec succès, ouvrez l'exécution et téléchargez l'artefact `Paranoia-Social-debug-APK`.
5. Décompressez l'artefact et installez `app-debug.apk` sur votre appareil Android. Android peut demander d'autoriser l'installation depuis cette source.

L'APK n'est pas fourni précompilé et ce projet n'a pas été compilé/testé dans cet environnement. Si GitHub Actions échoue, partagez le message d'erreur pour corriger le projet.
