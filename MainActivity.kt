package com.paranoia.social

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {
    private val bg = Color.rgb(10, 8, 16)
    private val panel = Color.rgb(25, 20, 35)
    private val panel2 = Color.rgb(35, 26, 49)
    private val purple = Color.rgb(168, 85, 247)
    private val pale = Color.rgb(222, 207, 255)
    private val muted = Color.rgb(166, 156, 181)
    private lateinit var content: LinearLayout
    private lateinit var header: LinearLayout
    private lateinit var nav: LinearLayout
    private var active = "Accueil"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        showApp()
    }

    private fun showApp() {
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(bg) }
        header = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(18, 14, 18, 12) }
        val mark = TextView(this).apply {
            text = "℘"; textSize = 34f; setTextColor(purple); gravity = Gravity.CENTER
            background = rounded(panel2, 18); layoutParams = LinearLayout.LayoutParams(dp(48), dp(48))
        }
        val brand = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(12, 0, 0, 0) }
        brand.addView(text("PARANOÏA", 20f, Color.WHITE, true))
        brand.addView(text("Un monde. Des voix. Des rencontres.", 10f, pale, false))
        header.addView(mark); header.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))
        val notif = TextView(this).apply { text = "♧"; textSize = 24f; setTextColor(Color.WHITE); gravity = Gravity.CENTER; setOnClickListener { navigate("Notifications") } }
        header.addView(notif, LinearLayout.LayoutParams(dp(36), dp(44)))
        val scroll = ScrollView(this).apply { isFillViewport = true }
        content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(16, 4, 16, 20) }
        scroll.addView(content)
        nav = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER; setPadding(4, 8, 4, 8); background = solid(Color.rgb(17, 13, 24)) }
        root.addView(header)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(nav)
        setContentView(root)
        render("Accueil")
    }

    private fun render(page: String) {
        active = page
        content.removeAllViews()
        nav.removeAllViews()
        when (page) {
            "Accueil" -> home()
            "Découvrir" -> discover()
            "Publier" -> publishPage()
            "Messages" -> messages()
            "Profil" -> profile()
            "Vidéos" -> videos()
            "Actualités" -> news()
            "Rencontres" -> meetings()
            "Notifications" -> notifications()
            "Paramètres" -> settings()
            else -> home()
        }
        listOf("Accueil" to "⌂", "Découvrir" to "⌕", "Publier" to "+", "Messages" to "▤", "Profil" to "●").forEach { (name, icon) ->
            val item = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(4, 2, 4, 2); setOnClickListener { navigate(name) } }
            item.addView(TextView(this).apply { text = icon; textSize = if (name == "Publier") 25f else 20f; gravity = Gravity.CENTER; setTextColor(if (active == name) purple else muted) })
            item.addView(TextView(this).apply { text = name; textSize = 10f; gravity = Gravity.CENTER; setTextColor(if (active == name) pale else muted) })
            nav.addView(item, LinearLayout.LayoutParams(0, dp(48), 1f))
        }
    }

    private fun home() {
        content.addView(text("Ton univers commence ici.", 22f, Color.WHITE, true))
        content.addView(text("Découvre, partage et crée des liens.", 13f, muted, false))
        space(16)
        val composer = card()
        composer.addView(text("Quoi de neuf ?", 16f, Color.WHITE, true))
        composer.addView(text("Partage une idée, une photo ou une vidéo avec la communauté.", 12f, muted, false))
        spaceIn(composer, 12)
        val actions = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        actions.addView(chip("▧ Photo") { navigate("Publier") }, weight())
        actions.addView(chip("▷ Vidéo") { navigate("Vidéos") }, weight())
        actions.addView(chip("♫ Audio") { navigate("Publier") }, weight())
        composer.addView(actions)
        content.addView(composer)
        space(16)
        content.addView(section("À découvrir", "Tout voir") { navigate("Découvrir") })
        val shortcuts = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        listOf("▷\nVidéos" to "Vidéos", "◎\nActualités" to "Actualités", "♡\nRencontres" to "Rencontres").forEach { (label, dest) ->
            shortcuts.addView(chip(label) { navigate(dest) }, weight())
        }
        content.addView(shortcuts)
        space(18)
        content.addView(section("Le fil de la communauté", "Actualités") { navigate("Actualités") })
        postCard("Aïcha", "Il y a 12 min", "Un nouveau jour, de nouvelles idées et de belles rencontres. 💜", "🌅  Un instant à partager")
        postCard("Kevin", "Il y a 38 min", "Quelle est la chose qui vous inspire le plus en ce moment ?", "💬  18 commentaires · 42 J'aime")
        space(8)
        content.addView(text("Aperçu de démonstration : les publications sont fictives pour le moment.", 11f, muted, false))
    }

    private fun discover() {
        content.addView(text("Découvrir", 24f, Color.WHITE, true))
        content.addView(text("Explore les différentes communautés de PARANOÏA.", 13f, muted, false)); space(16)
        listOf("Vidéos" to "Des formats courts à découvrir", "Actualités" to "Les nouvelles du monde", "Rencontres" to "Des liens amicaux et amoureux", "Groupes" to "Retrouve les personnes qui partagent tes passions", "Événements" to "Découvre ce qui se passe autour de toi").forEach { (title, desc) ->
            val c = card(); c.addView(text(title, 17f, pale, true)); c.addView(text(desc, 12f, muted, false)); c.addView(chip("Ouvrir  →") { navigate(title) }); content.addView(c); space(10)
        }
    }

    private fun publishPage() {
        content.addView(text("Créer une publication", 24f, Color.WHITE, true))
        content.addView(text("Exprime-toi et partage avec la communauté.", 13f, muted, false)); space(18)
        val edit = EditText(this).apply { hint = "Qu'as-tu envie de partager ?"; setHintTextColor(muted); setTextColor(Color.WHITE); textSize = 15f; minLines = 5; gravity = Gravity.TOP; setPadding(14, 14, 14, 14); background = rounded(panel, 16) }
        content.addView(edit, LinearLayout.LayoutParams(-1, dp(150)))
        space(12)
        listOf("Ajouter une photo", "Ajouter une vidéo", "Ajouter un audio", "Choisir l'audience").forEach { content.addView(chip("＋  $it") { toast("Cette option sera connectée dans une prochaine étape.") }); space(8) }
        content.addView(chip("Publier") { if (edit.text.toString().isBlank()) toast("Écris quelque chose avant de publier.") else toast("La publication sera activée après la connexion au serveur.") })
        space(8); content.addView(text("Dans cette version de départ, le formulaire est une maquette interactive : rien n'est encore envoyé en ligne.", 11f, muted, false))
    }

    private fun videos() {
        content.addView(text("Vidéos", 24f, Color.WHITE, true)); content.addView(text("Un espace pour les idées, les talents et les histoires.", 13f, muted, false)); space(16)
        val c = card(); c.gravity = Gravity.CENTER; c.addView(TextView(this).apply { text = "▷"; textSize = 60f; setTextColor(purple); gravity = Gravity.CENTER }); c.addView(text("Ton flux vidéo arrivera ici", 18f, Color.WHITE, true)); c.addView(text("Les vidéos seront chargées depuis le stockage sécurisé après la mise en place du serveur.", 12f, muted, false)); content.addView(c); space(12)
        content.addView(chip("＋  Préparer une vidéo") { toast("La publication vidéo sera ajoutée avec le stockage média.") })
    }

    private fun news() {
        content.addView(text("Actualités du monde", 24f, Color.WHITE, true)); content.addView(text("Des informations à lire, vérifier et partager.", 13f, muted, false)); space(14)
        listOf("À la une" to "Les principaux événements du jour", "Afrique" to "Actualités du continent africain", "Monde" to "Les nouvelles internationales", "Économie" to "Marchés, entreprises et emploi", "Technologie" to "Innovation et numérique", "Culture & sport" to "Les tendances et les résultats").forEach { (t, d) ->
            val c = card(); c.addView(text(t, 17f, pale, true)); c.addView(text(d, 13f, muted, false)); c.addView(text("Les articles apparaîtront ici depuis des sources identifiées.", 11f, muted, false)); content.addView(c); space(9)
        }
    }

    private fun meetings() {
        content.addView(text("Rencontres", 24f, Color.WHITE, true)); content.addView(text("Fais connaissance à ton rythme, en toute sécurité.", 13f, muted, false)); space(14)
        content.addView(chip("Rencontres amicales") { toast("La sélection amicale sera connectée aux profils plus tard.") }); space(8)
        content.addView(chip("Rencontres amoureuses · Adultes uniquement") { toast("Cette partie nécessitera une protection d'âge et des règles de sécurité dédiées.") }); space(12)
        val c = card(); c.addView(text("Des profils qui partagent tes centres d'intérêt", 17f, pale, true)); c.addView(text("Les suggestions, demandes de contact et préférences de visibilité seront ajoutées après la création des comptes.", 13f, muted, false)); content.addView(c)
    }

    private fun messages() {
        content.addView(text("Messages", 24f, Color.WHITE, true)); content.addView(text("Tes conversations au même endroit.", 13f, muted, false)); space(14)
        val search = EditText(this).apply { hint = "Rechercher une conversation"; setHintTextColor(muted); setTextColor(Color.WHITE); background = rounded(panel, 14); setPadding(14, 10, 14, 10) }
        content.addView(search); space(12)
        listOf("Lina" to "Salut ! Comment vas-tu ?", "Kevin" to "J'ai vu ta publication 💜", "Groupe des amis" to "Sarah : On se retrouve bientôt ?", "Maya" to "Photo envoyée").forEach { (name, msg) ->
            val c = card(); c.orientation = LinearLayout.HORIZONTAL; val avatar = TextView(this).apply { text = name.take(1); textSize = 20f; gravity = Gravity.CENTER; setTextColor(Color.WHITE); background = rounded(panel2, 30) }; c.addView(avatar, LinearLayout.LayoutParams(dp(46), dp(46)))
            val col = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(12, 2, 0, 0) }; col.addView(text(name, 15f, Color.WHITE, true)); col.addView(text(msg, 12f, muted, false)); c.addView(col); content.addView(c); space(7)
        }
        content.addView(text("Les conversations ci-dessus sont des exemples. La messagerie réelle sera activée après configuration du serveur.", 11f, muted, false))
    }

    private fun notifications() {
        content.addView(text("Notifications", 24f, Color.WHITE, true)); content.addView(text("Retrouve ici les nouveautés de ta communauté.", 13f, muted, false)); space(14)
        listOf("Bienvenue sur PARANOÏA" to "Ton espace social est prêt à être personnalisé.", "Les publications" to "Les mentions J'aime et commentaires apparaîtront ici.", "Messages" to "Tu seras averti lorsque tu recevras un nouveau message.", "Communauté" to "Les nouvelles demandes de contact apparaîtront ici.").forEach { (t, d) -> val c = card(); c.addView(text(t, 16f, pale, true)); c.addView(text(d, 12f, muted, false)); content.addView(c); space(8) }
    }

    private fun profile() {
        val hero = card(); hero.gravity = Gravity.CENTER
        val avatar = TextView(this).apply { text = "P"; textSize = 40f; setTextColor(Color.WHITE); gravity = Gravity.CENTER; background = rounded(panel2, 60) }
        hero.addView(avatar, LinearLayout.LayoutParams(dp(86), dp(86)))
        hero.addView(text("Ton profil", 22f, Color.WHITE, true)); hero.addView(text("@tonpseudo · Nouveau membre", 12f, muted, false)); hero.addView(chip("Modifier le profil") { toast("La gestion des profils sera connectée après l'inscription.") }); content.addView(hero)
        space(14); val stats = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        listOf("0\nPublications", "0\nAbonnés", "0\nAbonnements").forEach { stats.addView(chip(it) { toast("Les statistiques seront disponibles avec ton compte.") }, weight()) }; content.addView(stats)
        space(16); content.addView(section("Tes contenus", "Paramètres") { navigate("Paramètres") }); content.addView(text("Tes publications, photos et vidéos seront affichées ici.", 13f, muted, false))
    }

    private fun settings() {
        content.addView(text("Paramètres", 24f, Color.WHITE, true)); content.addView(text("Gère ton compte et ton expérience.", 13f, muted, false)); space(14)
        listOf("Compte" to "Informations et accès", "Confidentialité" to "Choisir qui peut voir ton profil", "Sécurité" to "Blocage et signalement", "Notifications" to "Gérer les alertes", "Apparence" to "Thème noir et violet", "Langue" to "Français", "Aide et support" to "Questions et assistance").forEach { (t,d) -> val c=card(); c.addView(text(t,16f,pale,true)); c.addView(text(d,12f,muted,false)); c.setOnClickListener { toast("Le réglage « $t » sera implémenté dans une prochaine étape.") }; content.addView(c); space(7) }
        content.addView(chip("Se déconnecter") { toast("La connexion utilisateur n'est pas encore configurée.") })
    }

    private fun postCard(name: String, time: String, body: String, footer: String) {
        val c = card()
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        val av = TextView(this).apply { text = name.take(1); textSize = 17f; gravity = Gravity.CENTER; setTextColor(Color.WHITE); background = rounded(panel2, 30) }
        row.addView(av, LinearLayout.LayoutParams(dp(42), dp(42)))
        val meta = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(10,0,0,0) }; meta.addView(text(name,15f,Color.WHITE,true)); meta.addView(text(time,11f,muted,false)); row.addView(meta)
        c.addView(row); spaceIn(c, 12); c.addView(text(body,14f,Color.WHITE,false)); spaceIn(c, 12)
        val media = TextView(this).apply { text = footer; textSize = 13f; setTextColor(pale); gravity = Gravity.CENTER; setPadding(8, 22, 8, 22); background = rounded(panel2, 14) }; c.addView(media)
        spaceIn(c, 10)
        val actions = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        actions.addView(chip("♡ J'aime") { toast("Les réactions seront enregistrées quand les comptes seront activés.") }, weight())
        actions.addView(chip("◯ Commenter") { toast("Les commentaires seront bientôt disponibles.") }, weight())
        actions.addView(chip("↗ Partager") { toast("Le partage sera disponible dans une prochaine version.") }, weight())
        c.addView(actions); content.addView(c); space(12)
    }

    private fun section(title: String, action: String, onClick: () -> Unit): View {
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(0, 4, 0, 8) }
        row.addView(text(title, 17f, Color.WHITE, true), LinearLayout.LayoutParams(0, -2, 1f))
        val btn = TextView(this).apply { text = action + "  ›"; textSize = 12f; setTextColor(pale); setOnClickListener { onClick() } }
        row.addView(btn); return row
    }

    private fun card(): LinearLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(14, 14, 14, 14); background = rounded(panel, 18) }
    private fun chip(label: String, click: () -> Unit): View = TextView(this).apply { text = label; textSize = 12f; gravity = Gravity.CENTER; setTextColor(pale); setPadding(10, 11, 10, 11); background = rounded(panel2, 12); setOnClickListener { click() } }
    private fun text(s: String, size: Float, color: Int, bold: Boolean): TextView = TextView(this).apply { text = s; textSize = size; setTextColor(color); if (bold) typeface = Typeface.create("sans-serif-medium", Typeface.BOLD) }
    private fun rounded(color: Int, radius: Int): GradientDrawable = GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat(); setStroke(dp(1), Color.rgb(57, 39, 77)) }
    private fun solid(color: Int): GradientDrawable = GradientDrawable().apply { setColor(color) }
    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
    private fun space(value: Int) { content.addView(View(this), LinearLayout.LayoutParams(1, dp(value))) }
    private fun spaceIn(parent: LinearLayout, value: Int) { parent.addView(View(this), LinearLayout.LayoutParams(1, dp(value))) }
    private fun weight() = LinearLayout.LayoutParams(0, -2, 1f).apply { setMargins(dp(3), dp(2), dp(3), dp(2)) }
    private fun navigate(page: String) = render(page)
    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
