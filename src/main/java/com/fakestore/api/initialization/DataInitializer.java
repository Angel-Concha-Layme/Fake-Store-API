package com.fakestore.api.initialization;


import com.fakestore.api.persistence.entity.Category;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.persistence.entity.Role;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.persistence.repository.CategoryRepository;
import com.fakestore.api.persistence.repository.ProductRepository;
import com.fakestore.api.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


// Esta clase se encarga de inicializar los datos de la base de datos
// Para verificar que no existen datos en la base de datos, se usarán métodos de los repositorios
// Si no existen datos, se inicializarán los datos de la base de datos
// Los datos que se inicializarán son:
// - Usuarios: User
// - Categorías: Category
// - Productos: Product
// - Pedidos: Order

// Se tienen que inicializar los datos de la base de datos en el orden indicado anteriormente, por sus dependencias

@Component
@AllArgsConstructor
public class DataInitializer implements InitializingBean {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void afterPropertiesSet() throws Exception {

        // Usuarios
        createUserIfNotExists("Juan", "juan@gmail.com","https://i.ibb.co/3RT7L1p/Juan.jpg");
        createUserIfNotExists("Manuel", "manuel@gmail.com","https://i.ibb.co/mqmFQfn/Manuel.jpg");
        createUserIfNotExists("Laura", "laura@gmail.com","https://i.ibb.co/0XpLV6c/laura.jpg");
        createUserIfNotExists("Carlos", "carlos@gmail.com", "https://i.ibb.co/VVDKfc5/Carlos.jpg");
        createUserIfNotExists("Maria", "maria@gmail.com", "https://i.ibb.co/syJ4yLh/Maria.jpg");
        createUserIfNotExists("Jose", "jose@gmail.com","https://i.ibb.co/DK51xM3/Jose.jpg");
        createUserIfNotExists("Ana", "ana@gmail.com","https://i.ibb.co/Rz7LFjJ/Ana.jpg" );
        createUserIfNotExists("David", "david@gmail.com","https://i.ibb.co/w7SfCdf/David.jpg");
        createUserIfNotExists("Sara", "sara@gmail.com","https://i.ibb.co/0MBZhrG/Sara.jpg");
        createUserIfNotExists("Pedro", "pedro@gmail.com","https://i.ibb.co/kMGxZch/Pedro.jpg" );

        // Categorías
        createCategoryIfNotExists("Electrónica", "Productos relacionados con dispositivos electrónicos, como smartphones, televisores, computadoras y más.","https://i.ibb.co/1QzkGf7/Electronica.png" );
        createCategoryIfNotExists("Moda", "Prendas y accesorios para todas las edades y estilos, incluyendo ropa casual, formal, deportiva y de temporada.","https://i.ibb.co/Chjv3k8/Moda.png");
        createCategoryIfNotExists("Alimentación", "Variedad de productos comestibles, incluyendo alimentos frescos, envasados, orgánicos, y especialidades gourmet.","https://i.ibb.co/hLpVpZX/Alimentaci-n.png");
        createCategoryIfNotExists("Hogar y Jardín", "Artículos para mejorar y embellecer tu espacio de vida, incluyendo muebles, decoración, herramientas de jardinería y electrodomésticos.","https://i.ibb.co/s2GpH7J/Hogar-y-jardin.png");
        createCategoryIfNotExists("Salud y Belleza", "Productos para el cuidado personal y bienestar, incluyendo cosméticos, productos de higiene, suplementos y equipos de fitness.","https://i.ibb.co/ZSsFGNL/Salud-y-belleza.png");
        createCategoryIfNotExists("Juguetes y Juegos", "Amplia gama de juguetes y juegos para niños y adultos, incluyendo juegos de mesa, juguetes educativos y electrónicos.","https://i.ibb.co/pr82Q23/Juguetes-y-juegos.png");
        createCategoryIfNotExists("Libros y Papelería", "Una diversa colección de libros, revistas, material de papelería y accesorios de escritura para todas las edades y gustos.","https://i.ibb.co/QjP1Zk0/Libros-y-papeleria.png");
        createCategoryIfNotExists("Deportes y Aire Libre", "Equipo y accesorios para una variedad de actividades deportivas y al aire libre, desde ciclismo hasta camping y fitness.", "https://i.ibb.co/VLCFsDF/Deportes-y-aire-libre.png");
        createCategoryIfNotExists("Mascotas", "Todo lo necesario para el cuidado de mascotas, incluyendo alimentos, juguetes, accesorios y productos de salud.", "https://i.ibb.co/8sQh7qw/Mascotas.png");
        createCategoryIfNotExists("Tecnología y Accesorios", "Productos innovadores y accesorios en el campo de la tecnología, incluyendo gadgets, dispositivos de audio y video, y periféricos de computadora.", "https://i.ibb.co/3Bdp2BR/Tecnologia-y-accesorios.png");

        // Productos
        createProductIfNotExists("Portátil Apple MacBook Pro", "Portátil de alto rendimiento con pantalla Retina de 13 pulgadas, chip Apple M1, 8 GB de RAM y 256 GB de SSD.", 1299.99, 75, 1, "https://i.ibb.co/cDj3dpX/Apple-Mac-Book-Pro.png");
        createProductIfNotExists("Smartphone Samsung Galaxy S21", "Smartphone con pantalla AMOLED de 6.2 pulgadas, cámara triple, 8 GB de RAM y 128 GB de almacenamiento.", 899.99, 50, 1, "https://i.ibb.co/mXkVjdS/Smartphone-Samsung-Galaxy-S21.png");
        createProductIfNotExists("Cámara Sony Alpha A7 III", "Cámara mirrorless con sensor full-frame de 24.2 MP, grabación de vídeo 4K HDR y sistema de enfoque automático rápido.", 1999.99, 30, 1, "https://i.ibb.co/ZHT2r1s/C-mara-Sony-Alpha-A7-III.png");
        createProductIfNotExists("Consola PlayStation 5", "Consola de juegos de última generación con gráficos en 4K, SSD ultrarrápido y retrocompatibilidad con juegos de PS4.", 499.99, 100, 1, "https://i.ibb.co/BcqzzgF/Consola-Play-Station-5.png");
        createProductIfNotExists("Auriculares Inalámbricos Bose QuietComfort 35", "Auriculares con cancelación de ruido, conexión Bluetooth, y hasta 20 horas de duración de batería.", 299.99, 40, 1, "https://i.ibb.co/YTb1Ybv/Auriculares-Inal-mbricos-Bose-Quiet-Comfort-35.png");

        createProductIfNotExists("Camiseta Nike Dri-FIT", "Camiseta deportiva de alto rendimiento con tecnología Dri-FIT para mantenerse seco y cómodo.", 39.99, 100, 2, "https://i.ibb.co/Hpv1gLk/Camiseta-Nike-Dri-FIT.png");
        createProductIfNotExists("Vestido Elegante de Noche", "Vestido largo elegante ideal para eventos nocturnos, disponible en varios colores y tallas.", 129.99, 30, 2, "https://i.ibb.co/WVY7wyD/Vestido-Elegante-de-Noche.png");
        createProductIfNotExists("Jeans Levi's 501 Original", "Jeans clásicos de corte recto, disponibles en una variedad de lavados y tallas.", 59.99, 80, 2, "https://i.ibb.co/yn9tWB2/Jeans-Levi-s-501-Original.png");
        createProductIfNotExists("Zapatillas Adidas Ultraboost", "Zapatillas de running con tecnología Boost para una mayor amortiguación y comodidad.", 179.99, 50, 2, "https://i.ibb.co/7jKp0rs/Zapatillas-Adidas-Ultraboost.png");
        createProductIfNotExists("Chaqueta de Cuero Estilo Motociclista", "Chaqueta de cuero genuino, ideal para un look casual o para motociclismo.", 199.99, 25, 2, "https://i.ibb.co/0hXWpfn/Chaqueta-de-Cuero-Estilo-Motociclista.png");

        createProductIfNotExists("Caja de Chocolates Gourmet", "Selección de chocolates finos de diferentes sabores y orígenes, presentados en una caja elegante.", 49.99, 60, 3, "https://i.ibb.co/j8TTvTk/Caja-de-Chocolates-Gourmet.png");
        createProductIfNotExists("Café Colombiano Premium", "Café de alta calidad, con notas frutales y un toque de dulzura, cultivado en las montañas de Colombia.", 19.99, 100, 3, "https://i.ibb.co/Gnrc0xc/Caf-Colombiano-Premium.png");
        createProductIfNotExists("Aceite de Oliva Virgen Extra", "Aceite de oliva puro, prensado en frío, ideal para cocinar y aderezar ensaladas.", 14.99, 80, 3, "https://i.ibb.co/7p3zhsR/Aceite-de-Oliva-Virgen-Extra.png");
        createProductIfNotExists("Surtido de Frutas Orgánicas", "Caja de frutas orgánicas frescas, incluyendo manzanas, naranjas, plátanos, y más.", 29.99, 50, 3, "https://i.ibb.co/Z8zRpNL/Surtido-de-Frutas-Org-nicas.png");
        createProductIfNotExists("Set de Especias del Mundo", "Colección de especias de varias partes del mundo, perfectas para experimentar con diferentes sabores en la cocina.", 34.99, 40, 3, "https://i.ibb.co/2F8vG7d/Set-de-Especias-del-Mundo.png");

        createProductIfNotExists("Sofá Seccional Moderno", "Sofá amplio y cómodo, perfecto para salas de estar, disponible en varios colores.", 899.99, 20, 4, "https://i.ibb.co/KNKVQ02/Sof-Seccional-Moderno.png");
        createProductIfNotExists("Set de Herramientas de Jardinería", "Kit completo con herramientas esenciales para el cuidado del jardín, incluyendo palas, tijeras y guantes.", 59.99, 50, 4, "https://i.ibb.co/kgKgWhP/Set-de-Herramientas-de-Jardiner-a.png");
        createProductIfNotExists("Lámpara de Pie Estilo Moderno", "Lámpara de pie elegante para iluminación ambiental, ideal para cualquier espacio del hogar.", 129.99, 40, 4, "https://i.ibb.co/CWvLBtk/L-mpara-de-Pie-Estilo-Moderno.png");
        createProductIfNotExists("Robot Aspirador Inteligente", "Aspiradora robotizada para limpieza automática de pisos, controlable vía smartphone.", 299.99, 30, 4, "https://i.ibb.co/YjzF2VM/Robot-Aspirador-Inteligente.png");
        createProductIfNotExists("Juego de Sábanas de Algodón Egipcio", "Juego de sábanas de alta calidad, suaves y duraderas, para camas de tamaño queen.", 99.99, 60, 4, "https://i.ibb.co/ryfPRZW/Juego-de-S-banas-de-Algod-n-Egipcio.png");

        createProductIfNotExists("Set de Maquillaje Profesional", "Kit completo de maquillaje con variedad de sombras, labiales, y más.", 79.99, 40, 5, "https://i.ibb.co/Br5FQWy/Set-de-Maquillaje-Profesional.png");
        createProductIfNotExists("Crema Hidratante Facial Orgánica", "Crema facial orgánica para todo tipo de piel, hidrata y rejuvenece.", 29.99, 70, 5, "https://i.ibb.co/MhFdBtN/Crema-Hidratante-Facial-Org-nica.png");
        createProductIfNotExists("Cepillo de Dientes Eléctrico", "Cepillo eléctrico con tecnología avanzada para una limpieza profunda.", 59.99, 50, 5, "https://i.ibb.co/Cs5s2C0/Cepillo-de-Dientes-El-ctrico.png");
        createProductIfNotExists("Suplementos Multivitamínicos", "Multivitamínicos para apoyar la salud general y el bienestar.", 19.99, 100, 5, "https://i.ibb.co/zP89hQn/Suplementos-Multivitam-nicos.png");
        createProductIfNotExists("Kit de Yoga con Mat y Accesorios", "Set completo para practicar yoga, incluye mat, bloques y correa.", 49.99, 30, 5, "https://i.ibb.co/MPPRvxp/Kit-de-Yoga-con-Mat-y-Accesorios.png");

        createProductIfNotExists("Juego de Mesa Monopoly", "Clásico juego de mesa Monopoly para toda la familia.", 24.99, 40, 6, "https://i.ibb.co/gPfxMfH/Juego-de-Mesa-Monopoly.png");
        createProductIfNotExists("Conjunto de Trenes de Madera", "Set de trenes de madera para niños, estimula la creatividad y la coordinación.", 34.99, 50, 6, "https://i.ibb.co/FwZ3PC1/Conjunto-de-Trenes-de-Madera.png");
        createProductIfNotExists("Consola de Videojuegos Retro", "Consola con juegos clásicos incorporados, ideal para nostálgicos.", 59.99, 30, 6, "https://i.ibb.co/jT8GgNT/Consola-de-Videojuegos-Retro.png");
        createProductIfNotExists("Muñeca Barbie Edición Especial", "Barbie edición especial coleccionable, con accesorios de moda.", 29.99, 25, 6, "https://i.ibb.co/2yXpcBV/Mu-eca-Barbie-Edici-n-Especial.png");
        createProductIfNotExists("Rompecabezas 3D de la Torre Eiffel", "Puzzle 3D de la Torre Eiffel, desafío divertido y educativo.", 19.99, 60, 6, "https://i.ibb.co/zV9DKgz/Rompecabezas-3-D-de-la-Torre-Eiffel.png");

        createProductIfNotExists("Novela 'Cien Años de Soledad'", "Famosa novela de Gabriel García Márquez, una obra maestra de la literatura.", 12.99, 80, 7, "https://i.ibb.co/KKmtYhr/Novela-Cien-A-os-de-Soledad.png");
        createProductIfNotExists("Set de Artículos de Papelería", "Kit completo de papelería incluyendo bolígrafos, cuadernos y más.", 24.99, 50, 7, "https://i.ibb.co/Nt4LsYK/Set-de-Art-culos-de-Papeler-a.png");
        createProductIfNotExists("Agenda Personal 2023", "Agenda elegante para organizar tu día a día, con espacio para notas y calendario.", 9.99, 100, 7, "https://i.ibb.co/WgKt7vW/Agenda-Personal-2023.png");
        createProductIfNotExists("Libro de Cocina 'Recetas del Mundo'", "Libro de recetas con platos internacionales, ilustraciones y guías paso a paso.", 29.99, 40, 7, "https://i.ibb.co/f8qMcL3/Libro-de-Cocina-Recetas-del-Mundo.png");
        createProductIfNotExists("Set de Rotuladores de Colores", "Colección de rotuladores de colores de alta calidad, perfectos para dibujo y diseño.", 19.99, 60, 7, "https://i.ibb.co/k4G1B7B/Set-de-Rotuladores-de-Colores.png");

        createProductIfNotExists("Bicicleta de Montaña", "Bicicleta robusta para terrenos difíciles, perfecta para aventuras al aire libre.", 299.99, 20, 8, "https://i.ibb.co/4NN13XR/Bicicleta-de-Monta-a.png");
        createProductIfNotExists("Carpa de Camping para 4 Personas", "Carpa espaciosa y resistente, ideal para camping familiar.", 199.99, 15, 8, "https://i.ibb.co/J2YSrjv/Carpa-de-Camping-para-4-Personas.png");
        createProductIfNotExists("Balón de Fútbol Adidas", "Balón de fútbol oficial tamaño y peso reglamentario, duradero y de alta calidad.", 29.99, 50, 8, "https://i.ibb.co/yNnbKvb/Bal-n-de-F-tbol-Adidas.png");
        createProductIfNotExists("Set de Pesca Completo", "Equipo de pesca con caña, carrete, anzuelos y más, perfecto para pescadores de todos los niveles.", 99.99, 25, 8, "https://i.ibb.co/9ykW5p5/Set-de-Pesca-Completo.png");
        createProductIfNotExists("Patineta Eléctrica", "Patineta eléctrica de alta velocidad y larga duración de batería, ideal para transporte urbano.", 399.99, 10, 8, "https://i.ibb.co/cCCrxtc/Patineta-El-ctrica.png");

        createProductIfNotExists("Alimento Seco para Perros", "Alimento balanceado y nutritivo para perros de todas las edades y tamaños.", 49.99, 100, 9, "https://i.ibb.co/3fQvm0s/Alimento-Seco-para-Perros.png");
        createProductIfNotExists("Rascador para Gatos", "Rascador de varios niveles para gatos, incluye juguetes y camas.", 69.99, 30, 9, "https://i.ibb.co/gmvyKJy/Rascador-para-Gatos.png");
        createProductIfNotExists("Acuario con Kit Completo", "Acuario de 20 litros con filtro, iluminación y decoración, ideal para principiantes.", 129.99, 20, 9, "https://i.ibb.co/cvFYrzP/Acuario-con-Kit-Completo.png");
        createProductIfNotExists("Correa Retráctil para Perros", "Correa retráctil de alta calidad, segura y cómoda para paseos diarios.", 19.99, 50, 9, "https://i.ibb.co/8j45qb3/Correa-Retr-ctil-para-Perros.png");
        createProductIfNotExists("Juguete Interactivo para Mascotas", "Juguete estimulante para mascotas, mantiene a los animales activos y entretenidos.", 14.99, 40, 9, "https://i.ibb.co/27qzrYw/Juguete-Interactivo-para-Mascotas.png");

        createProductIfNotExists("Smartwatch Avanzado Modelo X","Reloj inteligente con seguimiento de actividad física, notificaciones móviles, y resistencia al agua.",299.99,50,10,"https://i.ibb.co/WG8bmBJ/Smartwatch-Avanzado-Modelo-X.png");
        createProductIfNotExists("Drone con Cámara 4K","Drone equipado con cámara 4K y tecnología de estabilización de imagen, perfecto para fotografía aérea y videografía.",499.99,30,10,"https://i.ibb.co/hsHMZkr/Drone-con-C-mara-4-K.png");
        createProductIfNotExists("Teclado Mecánico Retroiluminado","Teclado mecánico de alta calidad con retroiluminación LED y teclas personalizables para una experiencia de escritura superior.",129.99,60,10,"https://i.ibb.co/dmwyFRg/Teclado-Mec-nico-Retroiluminado.png");
        createProductIfNotExists("Auriculares de Realidad Virtual","Experiencia inmersiva con estos auriculares de realidad virtual, compatibles con una amplia gama de juegos y aplicaciones VR.",399.99,40,10,"https://i.ibb.co/2jpCmsB/Auriculares-de-Realidad-Virtual.png");
        createProductIfNotExists("Altavoz Bluetooth Portátil","Altavoz Bluetooth compacto y portátil con excelente calidad de sonido y resistencia al agua, ideal para llevarlo a cualquier parte.",99.99,75,10,"https://i.ibb.co/LPYY3vD/Altavoz-Bluetooth-Port-til.png");

    }

    private void createUserIfNotExists(String username, String email, String avatar) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(username + "1234"));
            user.setEmail(email);
            user.setAvatar(avatar);
            user.setRole(Role.CUSTOMER);
            user.setCreatedAt(java.time.LocalDate.now());
            userRepository.save(user);
        }
    }

    private void createCategoryIfNotExists(String name, String description, String image) {
        if (!categoryRepository.existsByName(name)) {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            category.setImage(image);
            categoryRepository.save(category);
        }
    }
    private void createProductIfNotExists(String name, String description, double price, int stockQuantity, long categoryId, String imageUrl) {
        if (!productRepository.existsByName(name)) {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
            product.setImageUrl(imageUrl);
            product.setCreatedAt(java.time.LocalDate.now());
            product.setUpdatedAt(java.time.LocalDate.now());
            productRepository.save(product);
        }
    }
}
