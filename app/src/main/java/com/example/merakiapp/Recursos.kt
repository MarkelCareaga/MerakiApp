package com.example.merakiapp

// La Interfaz "Recursos" contiene todos los recursos necesarios para abrir la Activity Explicaciones
interface Recursos {

    companion object {
        // --------------- LISTA DE PANTALLAS ---------------
        var pantalla_Introduccion = "introduccion"
        var pantalla_PuertaSanJuan = "puerta_de_san_juan"
        var pantalla_Badatoz = "badatoz_estatua"
        var pantalla_FeriaPescado = "feria_del_pescado"
        var pantalla_Olatua = "olatua_estatua"
        var pantalla_Xixili = "xixili"
        var pantalla_Izaro = "isla_de_izaro"
        var pantalla_Gaztelugatxe = "gaztelugatxe"

        // --------------- LISTA DE AUDIOS PARA LAS EXPLICACIONES ---------------
        var audio_Introduccion = R.raw.audiointro
        var audio_PuertaSanJuan = R.raw.audiopuertadesanjuan
        var audio_Badatoz = R.raw.audiobadatoz
        var audio_FeriaPescado = R.raw.audioferiadelpescado
        var audio_Olatua = R.raw.audioolatua
        var audio_Xixili = R.raw.audioxixili
        var audio_Izaro = R.raw.audioisladeizaro
        var audio_Gaztelugatxe = R.raw.audiosanjuandegaztelugatxe
        var audio_Gritos = R.raw.gritoninos

        // --------------- LISTA DE AUDIOS PARA LOS JUEGOS ---------------
        var audio_Juego_Badatoz = R.raw.ahorateneisquecompletarelpuzzle
        var audio_Juego_FeriaPescado = R.raw.unelasimagenes
        var audio_Juego_Xixili = R.raw.ahoraostoca
        var audio_Juego_Izaro = R.raw.carreraderegatas
        var audio_Juego_Gaztelugatxe_Preguntas = R.raw.cuantaspuertashabia
        var audio_Juego_SopaLetras = R.raw.buscalos7
        var audio_Miren = R.raw.felicidades
        var audio_Patxi = R.raw.soisunosexcelentes

        // --------------- LISTA DE FONDOS ---------------
        var fondo_Introduccion = R.drawable.fondoprincipiofinal
        var fondo_PuertaSanJuan = R.drawable.fondopuertasanjuan
        var fondo_Badatoz = R.drawable.fondobadatoz
        var fondo_FeriaPescado = R.drawable.fondoferiapescado
        var fondo_Olatua = R.drawable.fondoolatua
        var fondo_Xixili = R.drawable.fondoxixili
        var fondo_Izaro = R.drawable.fondoizaro1
        var fondo_Gaztelugatxe = R.drawable.fondogaztelugatxe

        // --------------- LISTA DE MENSAJES ---------------
        val texto_Introduccion = "Hola! Nosotros somos Patxi y Miren, los protagonistas y los guías " +
                "de esta aplicación. Pertenecemos a una familia de marineros de Bermeo y seremos " +
                "quienes os darán todas las explicaciones necesarias para poder realizar correctamente " +
                "las actividades.Hola! Nosotros somos Patxi y Miren, los protagonistas y los guías de " +
                "esta aplicación. Pertenecemos a una familia de marineros de Bermeo y seremos quienes " +
                "os darán todas las explicaciones necesarias para poder realizar correctamente las actividades."

        val texto_PuertaSanJuan = "En el siglo XIV, Bermeo estaba rodeado de una gran muralla. Esa muralla " +
                "tenía 7 puertas en forma de arco para poder entrar al pueblo. Cada una de esas puertas " +
                "daban la opción de entrar al pueblo por las calles más famosas.\n\n" +
                "Hoy en día, solo queda en pie San Juan Portale, el único arco de los 7 que se construyeron. " +
                "Debajo del arco, podemos apreciar una huella de un pie. Cuenta la leyenda que esa huella es " +
                "de San Juan Bautista, y que fue uno de los únicos tres pasos que dio para llegar a la ermita " +
                "de San Juan de Gaztelugatxe.\n\n" +
                "Las 7 puertas con forma de arco que se construyeron en Bermeo se llamaban:\n\n" +
                "- Beiportale\n\n" +
                "- Santabarbaraportale - Erremedioportale\n\n" +
                "- Sanfrantziskoportale - Sanmigelportale\n\n" +
                "- Errenteriaportale\n\n" +
                "- Sanjuanportale\n\n"

        val texto_Badatoz = "Bermeo desde siempre ha sido un pueblo pesquero, por ello los bermeanos " +
                "son grandes navegadores y controlan el mar mejor que nadie.\n\n" +
                "Pero hace mucho tiempo, en un día de verano unos pescadores bermeanos se encontraron " +
                "con la flota Francesa. Estos, que tenían una tecnología más avanzada que los pescadores " +
                "vascos, les advirtieron que venía el mal tiempo. Por ello, el encargado de decidir si se " +
                "volvía a puerto (el señor) avisó a los barcos pesqueros de que volvieran. Pero algunos se " +
                "quedaron para poder aprovechar esos 4 días que les quedaban.\n\n" +
                "El problema fue que aquella galerna no era normal, aquella mar, que ellos bien conocían, se " +
                "puso en contra de los bermeanos. Aquel día 143 marineros murieron en aquella galerna. Poco a " +
                "poco empezaron a llegar las noticias a Bermeo y a las familias de dichos marineros.\n\n" +
                "Esta escultura simboliza el horror y tristeza de los bermeanos al enterarse de la noticia y " +
                "fue creada por Enrique Zubia Elorduy.\n\n"

        val texto_FeriaPescado = "La Arrain Azoka es la feria del pescado de Bermeo. Es una de las ferias y eventos " +
                "festivos más importante de Euskal Herria, y muestra la relación que los habitantes de Bermeo han " +
                "tenido con la mar desde hace siglos.\n\n" +
                "La feria tiene lugar en el parque de la Lamera, en Bermeo. Los vendedores muestran sus productos " +
                "en los puestos. Los visitantes podrán adquirir pescado en sus diferentes variedades (ahumado, " +
                "congelado, en conserva y precocinado) y marisco, todos ellos de muy buena calidad.\n\n" +
                "Cada año la feria se centra en un tema concreto, y al acabar el evento se entrega un premio a " +
                "personas, colectivos o empresas que hayan trabajado a favor de Bermeo o hayan ayudado a hacer al " +
                "pueblo más famoso. Además, durante la feria, se realizan muchas actividades para los visitantes y " +
                "la gente del pueblo, como por ejemplo un concurso de pintxos.\n\n"

        val texto_Olatua = "Esta escultura fue creada por el artista Néstor Basterretxea y está situada en el " +
                "puerto de Bermeo, pueblo en el que nació. La escultura fue inaugurada en abril de 2006, está " +
                "construida en un material llamado acero cortén, y cuenta con una altura de 8 metros. Según el " +
                "escultor, el material es “duro y asombroso, como el carácter de mi pueblo”.\n\n" +
                "La escultura cuenta con 5 elementos: tres olas y dos rocas. De noche, la escultura es iluminada " +
                "por unos focos, convirtiéndola en un lugar atractivo para los turistas. En la parte inferior " +
                "derecha, el escultor escribió unas palabras en euskera, las cuales, traducidas al castellano, " +
                "significan esto: “Bermeo, mi querido pueblo, eras la impresionante fuerza de una gigantesca ola.”\n\n"

        val texto_Xixili = "Érase una vez un pescador que mientras trabajaba, encontró a una lamia " +
                "que estaba herida.\n\n" +
                "El pescador quiso ayudarla, y pensó que lo mejor era llevarla a su pueblo, Bermeo. " +
                "Pero más tarde se arrepintió porque se dió cuenta de que no iba a ser bienvenida por " +
                "parte de la gente del pueblo. Entonces, el pescador decidió esconderla en la isla Izaro " +
                "y allí la curó.\n\n" +
                "Cuando la lamia se recuperó, se convirtió en su ayudante a la hora de pescar y ayudó al " +
                "pescador a conseguir los mejores peces.\n\n" +
                "Finalmente, después de pasar tanto tiempo juntos en el mar, el pescador y la lamia se " +
                "enamoraron y la lamia se convirtió en una mujer."

        val texto_Izaro = "Hoy en día, la isla de Izaro pertenece a Bermeo, aunque esto no siempre fue así. " +
                "La historia de cómo pasó a ser territorio de Bermeo es la más famosa de toda la costa de Urdaibai.\n\n" +
                "Hubo una época en la que dos pueblos querían quedarse con la isla: Mundaka y Bermeo. Para saber " +
                "quién se quedaría con ella, ambos pueblos decidieron enfrentarse en una regata.\n\n" +
                "La carrera tuvo lugar el día de la Magdalena, y el pueblo de Elantxobe actuó como juez. La prueba " +
                "consistía en que, por la mañana, al cantar el gallo, los remeros saldrían de cada uno de sus pueblos, " +
                "y la primera trainera que llegase a la isla se quedaría con ella. Ganó Bermeo, aunque según donde se " +
                "cuente esta anécdota la historia puede variar, ya que existen teorías de que los bermeanos hicieron " +
                "trampas emborrachando a los mundakeses o haciendo cantar al gallo antes de tiempo.\n\n"

        val texto_Gaztelugatxe = "San Juan de Gaztelugatxe es una isla con forma de cono que está situada " +
                "entre Bermeo y Bakio. En lo más alto de la isla hay una ermita, una iglesia pequeña. " +
                "La ermita está dedicada a San Juan Bautista. Según cuenta la leyenda de la isla, San " +
                "Juan Bautista llegó a Bermeo en barco y desembarcó en el puerto. Después, fue andando " +
                "hasta la isla y solamente dio tres pasos hasta llegar allí. Esos pasos dejaron su huella " +
                "en diferentes rocas de Bermeo.\n\n" +
                "La isla está unida a la tierra con un camino de piedras y hay unas escaleras para subir " +
                "hasta la ermita de San Juan de Gaztelugatxe. En total, hay 241 escaleras.\n\n" +
                "Hoy en día, todos los bermeanos celebramos la víspera de la fiesta de San Juan el día 24 " +
                "de junio. Y, el 25 de junio como tradición subimos las 241 escaleras y tocamos la campana " +
                "para tener buena suerte durante todo el año."
    }

}