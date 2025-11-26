package com.streaming.music.config;

import com.streaming.music.model.Musica;
import com.streaming.music.model.Playlist;
import com.streaming.music.model.Usuario;
import com.streaming.music.repository.MusicaRepository;
import com.streaming.music.repository.PlaylistRepository;
import com.streaming.music.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Configuration
public class DataInitializer {

    private final DataInitializerService dataInitializerService;

    public DataInitializer(DataInitializerService dataInitializerService) {
        this.dataInitializerService = dataInitializerService;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            dataInitializerService.initializeData();
        };
    }
}

@Service
class DataInitializerService {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializerService.class);

    private final UsuarioRepository usuarioRepository;
    private final MusicaRepository musicaRepository;
    private final PlaylistRepository playlistRepository;

    public DataInitializerService(
        UsuarioRepository usuarioRepository,
        MusicaRepository musicaRepository,
        PlaylistRepository playlistRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.musicaRepository = musicaRepository;
        this.playlistRepository = playlistRepository;
    }

    @Transactional
    public void initializeData() {
        if (usuarioRepository.count() > 0) {
            logger.info("Dados já existem no banco. Pulando inicialização.");
            return;
        }

        logger.info("Inicializando dados mockados...");

        // Gerar 200 nomes de usuários
        String[] primeirosNomes = {
            "João", "Maria", "Pedro", "Ana", "Carlos", "Julia", "Lucas", "Beatriz", "Rafael", "Fernanda",
            "Gabriel", "Camila", "Felipe", "Larissa", "Bruno", "Amanda", "Rodrigo", "Juliana", "Thiago", "Patricia",
            "Daniel", "Mariana", "Diego", "Isabela", "Leonardo", "Carolina", "Mateus", "Leticia", "Vinicius", "Natalia",
            "Gustavo", "Bianca", "Henrique", "Rafaela", "Marcos", "Aline", "Paulo", "Sabrina", "Ricardo", "Vanessa",
            "Eduardo", "Jessica", "Fábio", "Priscila", "André", "Tatiana", "Renato", "Giovanna", "Marcelo", "Claudia",
            "Alessandro", "Adriana", "Alexandre", "Alice", "Anderson", "Andrea", "Antonio", "Angelica", "Augusto", "Barbara",
            "Bernardo", "Bruna", "Caio", "Carla", "Cesar", "Cristina", "Danilo", "Daniela", "Denis", "Denise",
            "Edson", "Elaine", "Emerson", "Elisa", "Erick", "Erica", "Everton", "Evelyn", "Fabricio", "Fabiana",
            "Fernando", "Flavia", "Francisco", "Gabriela", "Guilherme", "Giovana", "Heitor", "Helena", "Hugo", "Heloisa",
            "Igor", "Ingrid", "Ivan", "Iris", "Jair", "Janaina", "Jean", "Jennifer", "Jose", "Joana"
        };

        String[] sobrenomes = {
            "Silva", "Santos", "Oliveira", "Costa", "Souza", "Ferreira", "Almeida", "Lima", "Gomes", "Ribeiro",
            "Martins", "Rodrigues", "Carvalho", "Barbosa", "Pereira", "Araujo", "Nascimento", "Rocha", "Mendes", "Dias",
            "Castro", "Cardoso", "Monteiro", "Nunes", "Ramos", "Freitas", "Teixeira", "Moreira", "Pinto", "Batista",
            "Vieira", "Correia", "Fernandes", "Cavalcanti", "Azevedo", "Melo", "Barros", "Campos", "Miranda", "Moura",
            "Soares", "Santana", "Machado", "Lopes", "Cunha", "Nogueira", "Cruz", "Farias", "Duarte", "Borges"
        };

        List<String> nomes = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            nomes.add(primeirosNomes[i % primeirosNomes.length] + " " + sobrenomes[i % sobrenomes.length]);
        }

        List<Usuario> usuarios = new ArrayList<>();
        logger.info("Criando 200 usuários...");
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Usuario usuario = usuarioRepository.save(new Usuario(nomes.get(i), 18 + random.nextInt(47)));
            usuarios.add(usuario);
        }
        logger.info("Criados 200 usuários");

        // Músicas base (200 músicas famosas)
        String[] musicasBase = {
            "Bohemian Rhapsody", "Stairway to Heaven", "Hotel California", "Smells Like Teen Spirit", "Sweet Child O' Mine",
            "Back in Black", "Comfortably Numb", "November Rain", "Imagine", "Hey Jude",
            "Let It Be", "Yesterday", "Come Together", "While My Guitar Gently Weeps", "Here Comes the Sun",
            "Purple Rain", "Like a Rolling Stone", "Satisfaction", "Paint It Black", "Sympathy for the Devil",
            "Gimme Shelter", "Angie", "Brown Sugar", "Start Me Up", "Miss You",
            "Don't Stop Believin'", "Any Way You Want It", "Faithfully", "Separate Ways", "Open Arms",
            "Kashmir", "Black Dog", "Whole Lotta Love", "Rock and Roll", "Immigrant Song",
            "Free Bird", "Simple Man", "Sweet Home Alabama", "Tuesday's Gone", "Gimme Three Steps",
            "Dream On", "Walk This Way", "Sweet Emotion", "I Don't Want to Miss a Thing", "Crazy",
            "Born to Run", "Thunder Road", "Dancing in the Dark", "The River", "Streets of Philadelphia",
            "One", "Enter Sandman", "Nothing Else Matters", "Master of Puppets", "Fade to Black",
            "Welcome to the Jungle", "Paradise City", "Patience", "Don't Cry", "Knockin' on Heaven's Door",
            "Livin' on a Prayer", "You Give Love a Bad Name", "Wanted Dead or Alive", "It's My Life", "Bad Medicine",
            "Another Brick in the Wall", "Wish You Were Here", "Time", "Money", "Shine On You Crazy Diamond",
            "Under Pressure", "Killer Queen", "Don't Stop Me Now", "We Are the Champions", "Radio Ga Ga",
            "Sweet Dreams", "Here I Go Again", "Is This Love", "Still of the Night", "Fool for Your Loving",
            "The Final Countdown", "Carrie", "Rock the Night", "Superstitious", "Cherokee",
            "Africa", "Hold the Line", "Rosanna", "I Won't Hold You Back", "Georgy Porgy",
            "Eye of the Tiger", "Burning Heart", "High on You", "I Can't Hold Back", "The Search Is Over",
            "More Than a Feeling", "Peace of Mind", "Foreplay/Long Time", "Rock & Roll Band", "Smokin'",
            "Carry On Wayward Son", "Dust in the Wind", "Point of Know Return", "Play the Game Tonight", "Hold On",
            "Roundabout", "Owner of a Lonely Heart", "Long Distance Runaround", "I've Seen All Good People", "Starship Trooper",
            "Tom Sawyer", "Limelight", "The Spirit of Radio", "Closer to the Heart", "Subdivisions",
            "Run to the Hills", "The Number of the Beast", "Hallowed Be Thy Name", "Fear of the Dark", "The Trooper",
            "Ace of Spades", "Overkill", "Killed by Death", "Bomber", "Motorhead",
            "Breaking the Law", "Living After Midnight", "You've Got Another Thing Comin'", "Painkiller", "Electric Eye",
            "Rainbow in the Dark", "Holy Diver", "The Last in Line", "We Rock", "Stand Up and Shout",
            "Man on the Silver Mountain", "Since You Been Gone", "Stone Cold", "Street of Dreams", "Spotlight Kid",
            "Bark at the Moon", "Crazy Train", "Mr. Crowley", "Flying High Again", "Shot in the Dark",
            "Highway to Hell", "T.N.T.", "You Shook Me All Night Long", "Thunderstruck", "Shoot to Thrill",
            "Smoke on the Water", "Highway Star", "Child in Time", "Perfect Strangers", "Burn",
            "Paranoid", "Iron Man", "War Pigs", "N.I.B.", "Fairies Wear Boots",
            "Sharp Dressed Man", "La Grange", "Tush", "Legs", "Gimme All Your Lovin'",
            "American Woman", "No Sugar Tonight", "These Eyes", "Undun", "Share the Land",
            "Spirit in the Sky", "In the Year 2525", "The Pusher", "Magic Carpet Ride", "Born to Be Wild",
            "All Right Now", "Fire and Water", "Wishing Well", "My Brother Jake", "The Hunter",
            "Black Magic Woman", "Oye Como Va", "Europa", "Evil Ways", "Samba Pa Ti",
            "Long Train Runnin'", "China Grove", "Listen to the Music", "Black Water", "Takin' It to the Streets",
            "Jessica", "Ramblin' Man", "Midnight Rider", "Blue Sky", "Melissa",
            "Blue Collar Man", "Come Sail Away", "Renegade", "Lady", "Babe"
        };

        // Gerar 1000 músicas (200 base + 800 geradas)
        List<String> musicasNomes = new ArrayList<>(Arrays.asList(musicasBase));

        String[] generos = {"Rock", "Pop", "Blues", "Jazz", "Metal", "Country", "Folk", "Indie", "Punk", "Alternative"};
        String[] adjetivos = {"Electric", "Acoustic", "Live", "Remix", "Unplugged", "Studio", "Classic", "Modern", "Vintage", "New"};

        for (int i = musicasBase.length; i < 1000; i++) {
            String musicaNome;
            if (i < 400) {
                musicaNome = generos[i % generos.length] + " Song " + (i - musicasBase.length + 1);
            } else if (i < 600) {
                musicaNome = adjetivos[i % adjetivos.length] + " " + generos[i % generos.length] + " " + (i - 399);
            } else if (i < 800) {
                musicaNome = "Track " + (i - 599) + " - " + generos[i % generos.length];
            } else {
                musicaNome = "Original Song " + (i - 799);
            }
            musicasNomes.add(musicaNome);
        }

        String[] artistas = {
            "Queen", "Led Zeppelin", "Eagles", "Nirvana", "Guns N' Roses",
            "AC/DC", "Pink Floyd", "The Beatles", "Prince", "The Rolling Stones",
            "Journey", "Lynyrd Skynyrd", "Aerosmith", "Bruce Springsteen", "Metallica",
            "Bon Jovi", "Whitesnake", "Europe", "Toto", "Survivor",
            "Boston", "Kansas", "Yes", "Rush", "Iron Maiden",
            "Motörhead", "Judas Priest", "Dio", "Rainbow", "Ozzy Osbourne",
            "Deep Purple", "Black Sabbath", "ZZ Top", "The Guess Who", "Norman Greenbaum",
            "Free", "Santana", "U2", "Coldplay", "Radiohead",
            "Foo Fighters", "Red Hot Chili Peppers", "Pearl Jam", "Soundgarden", "Alice in Chains",
            "The Who", "The Doors", "Jimi Hendrix", "Cream", "Eric Clapton",
            "David Bowie", "Elton John", "Billy Joel", "Paul McCartney", "John Lennon",
            "R.E.M.", "The Cure", "Depeche Mode", "New Order", "The Smiths",
            "Oasis", "Blur", "Pulp", "Suede", "The Verve",
            "Green Day", "Blink-182", "The Offspring", "Sum 41", "Simple Plan",
            "Linkin Park", "System of a Down", "Disturbed", "Slipknot", "Korn",
            "Pantera", "Megadeth", "Slayer", "Anthrax", "Testament",
            "Dream Theater", "Tool", "A Perfect Circle", "Porcupine Tree", "Opeth",
            "The Strokes", "Arctic Monkeys", "Franz Ferdinand", "The Killers", "Muse",
            "Kings of Leon", "The Black Keys", "Cage the Elephant", "Imagine Dragons", "Twenty One Pilots"
        };

        List<Musica> musicas = new ArrayList<>();
        logger.info("Criando 1000 músicas...");
        for (int i = 0; i < 1000; i++) {
            Musica musica = musicaRepository.save(
                new Musica(
                    musicasNomes.get(i),
                    artistas[random.nextInt(artistas.length)]
                )
            );
            musicas.add(musica);
            if ((i + 1) % 100 == 0) {
                logger.info("  Criadas " + (i + 1) + " músicas...");
            }
        }
        logger.info("Criadas 1000 músicas");

        String[] playlistNomes = {
            "Rock Clássico", "Anos 90", "Favoritas", "Para Treinar", "Relaxar",
            "Trabalho", "Festa", "Viagem", "Nostalgia", "Top Hits",
            "Acústico", "Metal Pesado", "Blues", "Jazz", "Pop Rock",
            "Indie", "Punk Rock", "Hard Rock", "Soft Rock", "Progressive Rock"
        };

        int playlistCount = 0;
        logger.info("Criando playlists (2 por usuário, ~100 músicas cada)...");
        for (int index = 0; index < usuarios.size(); index++) {
            Usuario usuario = usuarios.get(index);

            // Primeira playlist
            Playlist playlist1 = new Playlist(
                playlistNomes[random.nextInt(playlistNomes.length)] + " - " + usuario.getNome().split(" ")[0],
                usuario
            );
            // Adiciona de 90 a 110 músicas aleatórias
            List<Musica> musicasAleatorias1 = new ArrayList<>(musicas);
            Collections.shuffle(musicasAleatorias1, random);
            playlist1.getMusicas().addAll(musicasAleatorias1.subList(0, 90 + random.nextInt(21)));
            playlistRepository.save(playlist1);
            playlistCount++;

            // Segunda playlist
            Playlist playlist2 = new Playlist(
                playlistNomes[random.nextInt(playlistNomes.length)] + " - " + usuario.getNome().split(" ")[0] + " 2",
                usuario
            );
            // Adiciona de 90 a 110 músicas aleatórias diferentes
            List<Musica> musicasAleatorias2 = new ArrayList<>(musicas);
            Collections.shuffle(musicasAleatorias2, random);
            playlist2.getMusicas().addAll(musicasAleatorias2.subList(0, 90 + random.nextInt(21)));
            playlistRepository.save(playlist2);
            playlistCount++;

            if ((index + 1) % 50 == 0) {
                logger.info("  Criadas playlists para " + (index + 1) + " usuários...");
            }
        }

        logger.info("Dados mockados criados com sucesso!");
        logger.info("- 200 usuários");
        logger.info("- 1000 músicas");
        logger.info("- " + playlistCount + " playlists (2 por usuário, ~100 músicas cada)");
    }
}
