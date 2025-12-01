# Proyecto-Final-Sanrio

## Integrantes:

- María José Osorio Mercado
- Natalia Sofía Durango Hernandez

### Esté es el link del prototipo hecho en figma: [Link](https://www.figma.com/design/hsPHD388MeI3sndDZZRg9j/Prototipo-Sanrio?node-id=0-1&t=HxdwEY4NAObecfiw-1)

---

## Manual de Usuario

### 1. Inicio de Sesión y Registro

#### 1.1 Iniciar Sesión
1. Al abrir la aplicación, se muestra la pantalla de inicio de sesión.
2. Ingrese su correo electrónico en el campo "Email".
3. Ingrese su contraseña en el campo "Password".
4. Haga clic en el botón "Login".
5. Si las credenciales son correctas, será redirigido al catálogo de productos.
6. Si las credenciales son incorrectas, aparecerá un mensaje de error indicando el problema.

#### 1.2 Registrarse
1. En la pantalla de inicio de sesión, haga clic en "Sign in" para cambiar a la vista de registro.
2. Complete todos los campos requeridos:
   - **Nombre**: Su nombre completo
   - **ID**: Un número de identificación positivo
   - **Email**: Su correo electrónico válido
   - **Password**: Su contraseña (mínimo 6 caracteres)
   - **Confirm Password**: Confirme su contraseña
3. Haga clic en el botón "Accept" para registrar su cuenta.
4. Si todos los campos son válidos, recibirá un mensaje de confirmación y será redirigido a la pantalla de inicio de sesión.
5. Si hay errores, aparecerán mensajes indicando qué corregir:
   - Campos incompletos
   - Email inválido
   - ID inválido (debe ser un número positivo)
   - Contraseñas no coinciden
   - Contraseña muy corta (mínimo 6 caracteres)
   - Email ya registrado

#### 1.3 Modo Administrador (Easter Egg)
Para activar el modo administrador durante el registro:
1. Haga clic 2 veces en la imagen de Hello Kitty
2. Haga clic 1 vez en la imagen de Cinnamoroll
3. Haga clic 1 vez en la imagen de Pochacco
4. Si la secuencia es correcta, aparecerá un mensaje indicando que el modo administrador está activo.
5. Al registrarse con el modo administrador activo, su cuenta será creada con rol de administrador.
6. El modo administrador se desactiva automáticamente después de 10 segundos si no se completa el registro.

### 2. Navegación del Catálogo

#### 2.1 Visualización de Personajes
1. Al ingresar al catálogo, verá una sección superior con tarjetas de personajes de Sanrio.
2. Cada tarjeta muestra la imagen y el nombre del personaje.
3. Los personajes disponibles incluyen: Hello Kitty, Cinnamoroll, Kuromi, My Melody, y otros.

#### 2.2 Visualización de Productos
1. Debajo de los personajes, se muestra una cuadrícula con todos los productos disponibles.
2. Cada producto se muestra en una tarjeta con:
   - Imagen del producto
   - Nombre del producto
   - Precio (en USD o COP según el idioma seleccionado)
   - Botón "Add to Bag"
3. Los productos se cargan de forma incremental (20 productos por lote) para mejorar el rendimiento.
4. Al hacer scroll hacia abajo, se cargan automáticamente más productos.

#### 2.3 Filtrado por Personaje
1. Haga clic en la tarjeta de un personaje para filtrar los productos.
2. Solo se mostrarán los productos asociados a ese personaje.
3. Se mostrará información adicional del personaje seleccionado (nombre y descripción).
4. Para ver todos los productos nuevamente, haga clic en el logo de Sanrio en la parte superior.

#### 2.4 Información del Personaje Seleccionado
1. Al seleccionar un personaje, aparecerá un panel con información:
   - Nombre del personaje
   - Descripción del personaje (en el idioma actual)
2. Esta información se actualiza automáticamente cuando cambia el idioma.

### 3. Vista de Detalles de Producto

#### 3.1 Acceso a la Vista de Detalles
1. Haga clic en la imagen de cualquier producto en el catálogo.
2. La vista del catálogo será reemplazada por la vista de detalles del producto.

#### 3.2 Carrusel de Imágenes
1. En la parte izquierda de la vista de detalles, se muestra una imagen principal del producto.
2. Debajo de la imagen principal, hay miniaturas (thumbnails) de todas las imágenes disponibles.
3. Haga clic en una miniatura para cambiar la imagen principal.
4. Use las flechas izquierda y derecha para navegar entre imágenes.
5. Las flechas solo aparecen si hay más de una imagen disponible.

#### 3.3 Información del Producto
1. En la parte derecha, se muestra:
   - Nombre del producto
   - Precio (formateado según el idioma: USD o COP)
   - Descripción del producto (en el idioma actual)
2. La descripción cambia automáticamente cuando se cambia el idioma.

#### 3.4 Selector de Cantidad
1. Use los botones "+" y "-" para aumentar o disminuir la cantidad deseada.
2. La cantidad mínima es 1.
3. La cantidad seleccionada se muestra entre los botones.

#### 3.5 Agregar al Carrito
1. Haga clic en el botón "Add to Bag".
2. El producto se agregará al carrito la cantidad de veces seleccionada.
3. Si el producto ya está en el carrito, aparecerá un mensaje de advertencia.
4. Si la operación es exitosa, aparecerá un mensaje de confirmación.

#### 3.6 Productos Recomendados
1. En la parte inferior de la vista de detalles, hay una sección "You might like" con productos recomendados.
2. Los productos recomendados se filtran por categoría, mostrando productos similares.
3. Puede hacer scroll horizontal para ver más productos recomendados.
4. Haga clic en cualquier producto recomendado para ver sus detalles.
5. Puede agregar productos recomendados directamente al carrito.

#### 3.7 Regreso al Catálogo
1. Haga clic en el logo de Sanrio en la parte superior para volver al catálogo.
2. El catálogo se restaurará con el estado anterior (filtros aplicados).

### 4. Wishlist (Lista de Deseos)

#### 4.1 Agregar Productos a Wishlist
1. En cualquier tarjeta de producto del catálogo, haga clic en el icono de corazón.
2. El producto se agregará a su lista de deseos.
3. Un producto no puede estar simultáneamente en el carrito y en la wishlist.

#### 4.2 Ver Wishlist
1. Haga clic en "Wishlist" en la barra de navegación superior.
2. Se abrirá un panel overlay mostrando todos los productos en su lista de deseos.
3. El panel muestra:
   - Imagen del producto
   - Nombre del producto
   - Precio (actualizado según el idioma)
   - Botón "X" para eliminar
   - Icono de bolsa para mover al carrito

#### 4.3 Eliminar Productos de Wishlist
1. En el panel de wishlist, haga clic en el botón "X" de cualquier producto.
2. El producto será eliminado de su lista de deseos.

#### 4.4 Mover a Carrito
1. En el panel de wishlist, haga clic en el icono de bolsa de cualquier producto.
2. El producto se moverá del wishlist al carrito.
3. El producto desaparecerá del wishlist y aparecerá en el carrito.

### 5. Carrito de Compras

#### 5.1 Agregar Productos al Carrito
1. Desde el catálogo: Haga clic en "Add to Bag" en cualquier producto.
2. Desde la vista de detalles: Seleccione la cantidad y haga clic en "Add to Bag".
3. Los productos se agregarán al carrito con la cantidad especificada.

#### 5.2 Ver Carrito
1. Haga clic en "Cart" en la barra de navegación superior.
2. Se abrirá un panel overlay mostrando todos los productos en su carrito.
3. El panel muestra:
   - Imagen del producto
   - Nombre del producto
   - Precio (actualizado según el idioma)
   - Cantidad de cada producto
   - Botón "X" para eliminar
   - Icono de corazón para mover a wishlist
   - Total de la compra
   - Botón "Checkout" para proceder al pago

#### 5.3 Eliminar Productos del Carrito
1. En el panel del carrito, haga clic en el botón "X" de cualquier producto.
2. El producto será eliminado de su carrito.

#### 5.4 Mover a Wishlist
1. En el panel del carrito, haga clic en el icono de corazón de cualquier producto.
2. El producto se moverá del carrito al wishlist.
3. El producto desaparecerá del carrito y aparecerá en el wishlist.

#### 5.5 Ir a Checkout
1. En el panel del carrito, haga clic en el botón "Checkout".
2. Será redirigido a la pasarela de pago.

### 6. Pasarela de Pago (Checkout)

#### 6.1 Acceso al Checkout
1. Desde el carrito, haga clic en el botón "Checkout".
2. Se abrirá un panel overlay con la pasarela de pago.

#### 6.2 Selección de Método de Pago
La aplicación ofrece tres métodos de pago:
- **Tarjeta de crédito**: Para pagos con tarjeta
- **Nequi**: Billetera digital colombiana
- **DaviPlata**: Billetera digital colombiana

1. Haga clic en la opción del método de pago deseado.
2. Solo puede estar seleccionado un método a la vez.
3. Aparecerán los campos específicos para el método seleccionado.

#### 6.3 Ingreso de Datos de Pago

**Para Tarjeta de Crédito:**
1. Ingrese el número de tarjeta (16 dígitos).
2. Ingrese la fecha de expiración (MM/YY).
3. Ingrese el código CVV (3 dígitos).

**Para Nequi:**
1. Ingrese el nombre del titular de la cuenta.
2. Ingrese el número de cuenta.
3. Ingrese la clave dinámica.

**Para DaviPlata:**
1. Ingrese el nombre del titular de la cuenta.
2. Ingrese el número de cuenta.
3. Ingrese el código de pago.

#### 6.4 Resumen de Compra
1. En el panel derecho, se muestra un resumen de todos los productos en el carrito.
2. Cada producto muestra:
   - Imagen
   - Nombre
   - Precio unitario
   - Cantidad
3. Se muestra el precio total de todos los productos.
4. El total se actualiza automáticamente según el idioma (USD o COP).

#### 6.5 Finalización de Compra
1. Complete todos los campos requeridos del método de pago seleccionado.
2. Revise el resumen de compra.
3. Haga clic en el botón "REALIZAR COMPRA" (o "COMPLETE PURCHASE" en inglés).
4. Si todos los campos están completos, la compra se procesará:
   - Los productos se moverán del carrito al historial de compras
   - Aparecerá un mensaje de confirmación
   - Será redirigido al catálogo
5. Si faltan campos, aparecerá un mensaje indicando qué completar.

### 7. Historial de Compras

#### 7.1 Acceso al Historial
1. Haga clic en "Purchase History" en la barra de navegación superior.
2. La vista del catálogo será reemplazada por el panel de historial de compras.

#### 7.2 Visualización de Compras
1. El panel muestra una tabla con todas sus compras realizadas.
2. La tabla incluye las siguientes columnas:
   - **N° DE PRODUCTO**: Número de identificación del producto
   - **NOMBRE PRODUCTO**: Nombre del producto comprado
   - **PRECIO PRODUCTO**: Precio pagado (actualizado según idioma)
   - **CORREO COMPRADOR**: Su correo electrónico
3. En el lado izquierdo, hay un banner decorativo con la imagen de Sanrio.

#### 7.3 Regreso al Catálogo
1. Haga clic en el logo de Sanrio en la parte superior para volver al catálogo.
2. El catálogo se restaurará con el estado anterior.

### 8. Gestión de Cuenta

#### 8.1 Acceso al Menú de Cuenta
1. Haga clic en "My Account" en la barra de navegación superior.
2. Se desplegará un menú con las siguientes opciones:
   - Change password
   - Translate
   - Logout

#### 8.2 Cambio de Contraseña
1. En el menú de cuenta, haga clic en "Change password".
2. Se abrirá un panel overlay con los siguientes campos:
   - Current Password (Contraseña actual)
   - New Password (Nueva contraseña)
   - Confirm New Password (Confirmar nueva contraseña)
3. Complete todos los campos.
4. Haga clic en "Change" para actualizar su contraseña.
5. Si la contraseña actual es incorrecta o las nuevas contraseñas no coinciden, aparecerá un mensaje de error.
6. Si la operación es exitosa, aparecerá un mensaje de confirmación.

#### 8.3 Cambio de Idioma
1. En el menú de cuenta, haga clic en "Translate".
2. El idioma cambiará entre Español e Inglés.
3. Todos los textos de la interfaz se actualizarán automáticamente:
   - Menús y navegación
   - Etiquetas de productos
   - Precios (USD para inglés, COP para español)
   - Mensajes y alertas
4. Aparecerá un mensaje confirmando el cambio de idioma.

#### 8.4 Cerrar Sesión
1. En el menú de cuenta, haga clic en "Logout".
2. Su sesión se cerrará y será redirigido a la pantalla de inicio de sesión.
3. Todos los datos de su sesión se limpiarán.

---

## Manual de Desarrollador

A continuación se presenta la explicación y demostración de la solución en el código fuente (contenido para desarrolladores).

### Métodos Importantes

#### App.java
- **`start(Stage stage)`**: Método principal de inicialización de la aplicación JavaFX. Carga el archivo FXML `Login_and_Signing.fxml` y configura la escena inicial. Es el punto de entrada de la aplicación.

#### Administrador_Singleton.java
- **`getAdministrador()`**: Implementa el patrón Singleton para garantizar una única instancia del administrador en toda la aplicación. Retorna la instancia única que gestiona todas las estructuras de datos.
- **`inicializarDatos()`**: Carga los datos iniciales de personajes y productos desde los archivos del sistema. Debe ser llamado al inicio de la aplicación para poblar las listas.

#### LanguageManager.java
- **`getInstance()`**: Obtiene la instancia única del gestor de idiomas (patrón Singleton).
- **`setLanguage(String language)`**: Cambia el idioma de la aplicación entre "es" (español) y "en" (inglés). Recarga el ResourceBundle correspondiente.
- **`formatPrice(float precioUSD)`**: Formatea el precio según el idioma actual. Si el idioma es español, convierte a pesos colombianos (COP) usando la tasa 1 USD = 3800 COP. Si es inglés, muestra en dólares (USD).
- **`getString(String key)`**: Obtiene una cadena de texto traducida desde el archivo de recursos según la clave proporcionada.

#### Pila_Stack_De_Productos.java
- **`setPushProducto(Stack<Producto> stack, Producto producto)`**: Agrega un producto a una pila específica (carrito, wishlist o historial) si no existe previamente.
- **`eliminarPorCorreoYIdntificacion(Stack<Producto> stack, String correo, String identificacion)`**: Elimina un producto específico de una pila basándose en el correo del usuario y la identificación del producto.
- **`moverCarritoAHistorial(String correoUsuario)`**: Transfiere todos los productos del carrito de un usuario al historial de compras. Asigna la fecha de compra actual a cada producto y guarda los cambios en archivos.
- **`obtenerProductosHistorialPorUsuario(String correoUsuario)`**: Filtra y retorna todos los productos del historial de compras pertenecientes a un usuario específico.
- **`obtenerProductosCarritoPorUsuario(String correoUsuario)`**: Filtra y retorna todos los productos del carrito pertenecientes a un usuario específico.
- **`obtenerProductosWishlistPorUsuario(String correoUsuario)`**: Filtra y retorna todos los productos de la wishlist pertenecientes a un usuario específico.
- **`agregarAlCarrito(Producto producto, String correoUsuario)`**: Agrega un producto al carrito de un usuario. Valida que el producto no esté ya en el carrito o en la wishlist.
- **`agregarAWishlist(Producto producto, String correoUsuario)`**: Agrega un producto a la wishlist de un usuario. Valida que el producto no esté ya en la wishlist o en el carrito.
- **`moverDeWishlistACarrito(String identificacion, String correoUsuario)`**: Mueve un producto de la wishlist al carrito del usuario.
- **`moverDeCarritoAWishlist(String identificacion, String correoUsuario)`**: Mueve un producto del carrito a la wishlist del usuario.
- **`guardarEnArchivoTXT(Stack<Producto> stack, String nombre_archivo)`**: Persiste los datos de una pila en un archivo de texto en la carpeta `ArchivesTXT`.
- **`cargarDesdeArchivoTXT(Stack<Producto> stack, String nombre_archivo)`**: Carga los datos de una pila desde un archivo de texto.

#### Lista_Doble_Usuarios.java
- **`buscarCorreo(String correo)`**: Busca un usuario en la lista doblemente enlazada por su correo electrónico. Retorna el nodo del usuario si existe, null en caso contrario.
- **`crearUsuario(TextField txtNombre, TextField txtIdentificacion, TextField txtCorreo, PasswordField txtContrasena, String roll)`**: Crea un nuevo usuario con validaciones. Valida que el correo no exista, que la identificación sea válida y que todos los campos estén completos.
- **`agregarUsuario(String nombre, int identificacion, String correo, String contrasena, String roll)`**: Agrega un nuevo usuario a la lista doblemente enlazada.
- **`validarLogin(String correo, String contrasena)`**: Valida las credenciales de inicio de sesión. Retorna el nodo del usuario si las credenciales son correctas.
- **`guardarEnArchivoDeTexto()`**: Guarda todos los usuarios en el archivo `Usuarios.txt`.
- **`cargarDesdeArchivoDeTexto()`**: Carga todos los usuarios desde el archivo `Usuarios.txt`.

#### Lista_Doble_Personajes.java
- **`buscarNombre(String nombre)`**: Busca un personaje en la lista por su nombre. Retorna el nodo del personaje si existe.
- **`getPersonajes()`**: Retorna una lista observable con todos los personajes para su uso en componentes JavaFX.
- **`cargarPersonajesDesdeArchivos()`**: Carga todos los personajes desde los archivos `Data.txt` ubicados en las carpetas de cada personaje.

#### Lista_Doble_Productos.java
- **`buscarIdentificacion(String identificacion)`**: Busca un producto en la lista por su identificación. Retorna el nodo del producto si existe.
- **`getProductos()`**: Retorna una lista observable con todos los productos para su uso en componentes JavaFX.
- **`cargarProductosDesdeArchivos()`**: Carga todos los productos desde los archivos de texto ubicados en las carpetas de productos de cada personaje.

#### Controller_Catalog_Section.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa el controlador del catálogo. Carga personajes y productos, configura la interfaz y establece los listeners necesarios.
- **`mostrarDetallesProducto(Nodo_Producto nodoProducto)`**: Reemplaza el contenido del ScrollPane principal con la vista de detalles del producto seleccionado. Guarda el contenido del catálogo para poder restaurarlo después.
- **`volverAlCatalogo()`**: Restaura la vista del catálogo reemplazando el contenido actual del ScrollPane con el contenido guardado previamente. Resetea el scroll a la parte superior.
- **`agregarAlCarrito(Nodo_Producto nodoProducto)`**: Agrega un producto al carrito del usuario actual. Valida que el usuario esté logueado y que el producto no esté ya en el carrito o wishlist.
- **`handlePurchaseHistory(MouseEvent event)`**: Maneja el clic en el botón de historial de compras. Carga y muestra el panel de historial reemplazando el contenido del catálogo.
- **`actualizarTextos()`**: Actualiza todos los textos de la interfaz según el idioma actual usando el LanguageManager.
- **`actualizarPreciosProductos()`**: Actualiza todos los precios mostrados según la moneda correspondiente al idioma actual (USD o COP).
- **`cargarProductosIncrementales()`**: Carga productos en lotes de 20 para mejorar el rendimiento. Se ejecuta automáticamente cuando el usuario hace scroll.

#### Controller_Login_and_Signing.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa el controlador de login. Carga usuarios desde archivo, configura los eventos de los personajes para el modo administrador y actualiza textos.
- **`handleLogin(ActionEvent event)`**: Procesa el intento de inicio de sesión. Valida credenciales y redirige al catálogo si son correctas.
- **`handleAccept(ActionEvent event)`**: Procesa el registro de un nuevo usuario. Valida todos los campos, verifica que el correo no exista y crea el usuario con el rol correspondiente (administrador si el modo admin está activo).
- **`verificarAdminMode()`**: Verifica si se ha activado el modo administrador mediante la secuencia de clics en los personajes.
- **`validarCorreo(String correo)`**: Valida que el formato del correo electrónico sea válido usando expresiones regulares.

#### Controller_Product_Detail_View.java
- **`setProducto(Nodo_Producto nodoProducto)`**: Establece el producto a mostrar en la vista de detalles. Carga la información, imágenes y productos recomendados.
- **`cargarImagenesProducto()`**: Carga todas las imágenes disponibles del producto desde el sistema de archivos. Crea thumbnails para navegación.
- **`mostrarImagen(int index)`**: Muestra una imagen específica del carrusel y actualiza la selección visual en los thumbnails.
- **`cargarProductosRecomendados()`**: Filtra y muestra productos recomendados basándose en las categorías del producto actual.
- **`handleAddToBag(ActionEvent event)`**: Agrega el producto al carrito con la cantidad seleccionada. Valida que el usuario esté logueado.
- **`actualizarTextos()`**: Actualiza los textos de la vista según el idioma actual.
- **`actualizarPrecios()`**: Actualiza los precios mostrados según la moneda del idioma actual.

#### Controller_Checkout_View.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa la vista de checkout. Carga productos del carrito, calcula el total y configura el método de pago inicial.
- **`cargarProductos()`**: Carga todos los productos del carrito del usuario y los agrupa por identificación para mostrar cantidades.
- **`calcularTotal()`**: Calcula el precio total de todos los productos en el carrito.
- **`handleCompletePurchase()`**: Procesa la finalización de la compra. Valida los campos del método de pago seleccionado, mueve productos al historial y muestra mensajes de confirmación.
- **`mostrarCamposMetodoPago(String method)`**: Muestra u oculta los campos de entrada según el método de pago seleccionado (Tarjeta de crédito, Nequi o DaviPlata).
- **`actualizarTextos()`**: Actualiza todos los textos de la interfaz según el idioma actual.
- **`actualizarPrecios()`**: Actualiza los precios mostrados y recalcula el total según la moneda del idioma actual.

#### Controller_Cart_Panel.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa el panel del carrito. Carga productos del usuario y configura la interfaz.
- **`refresh()`**: Recarga los productos del carrito desde los archivos y actualiza la visualización.
- **`cargarProductos()`**: Carga y muestra todos los productos del carrito del usuario actual.
- **`handleCheckout()`**: Maneja el clic en el botón de checkout. Ejecuta el callback configurado para abrir la pasarela de pago.

#### Controller_Wishlist_Panel.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa el panel de wishlist. Carga productos del usuario y configura la interfaz.
- **`refresh()`**: Recarga los productos de la wishlist desde los archivos y actualiza la visualización.
- **`cargarProductos()`**: Carga y muestra todos los productos de la wishlist del usuario actual.

#### Controller_Purchase_History_Panel.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa el panel de historial. Configura la tabla y carga los datos del historial.
- **`cargarHistorial()`**: Carga los productos del historial del usuario actual y los muestra en la tabla.
- **`refresh()`**: Recarga los datos del historial desde los archivos y actualiza la tabla.
- **`actualizarPrecios()`**: Actualiza los precios mostrados en la tabla según la moneda del idioma actual.

#### Controller_Change_Password_Panel.java
- **`initialize(URL url, ResourceBundle rb)`**: Inicializa el panel de cambio de contraseña.
- **`handleChangePassword()`**: Procesa el cambio de contraseña. Valida la contraseña actual y que las nuevas contraseñas coincidan.

---

### Tabla de Clases

<table align="center">
  <tr>
    <th colspan="3"><h3 align="center">Tabla de Clases</h3></th>
  </tr>
  <tr>
    <th>Clase</th>
    <th>Paquete</th>
    <th>Descripción</th>
  </tr>
  <tr>
    <td>App.java</td>
    <td>Main</td>
    <td>Clase principal de la aplicación JavaFX. Se encarga de inicializar la aplicación, cargar la vista de login y lanzar la ventana principal.</td>
  </tr>
  <tr>
    <td>Administrador_Singleton.java</td>
    <td>Models.DataStructures</td>
    <td>Implementa el patrón Singleton para gestionar una única instancia del administrador. Contiene referencias a todas las estructuras de datos principales (listas de usuarios, personajes, productos y pilas de productos).</td>
  </tr>
  <tr>
    <td>LanguageManager.java</td>
    <td>Models.DataStructures</td>
    <td>Gestiona el cambio de idioma de la aplicación (español/inglés). Implementa Singleton y proporciona métodos para obtener textos traducidos y formatear precios según el idioma.</td>
  </tr>
  <tr>
    <td>Pila_Stack_De_Productos.java</td>
    <td>Models.DataStructures</td>
    <td>Gestiona tres pilas Stack para productos: carrito de compras, wishlist e historial de compras. Implementa métodos para agregar, eliminar, mover productos entre pilas y persistir datos en archivos .txt.</td>
  </tr>
  <tr>
    <td>Lista_Doble_Usuarios.java</td>
    <td>Models.DataStructures</td>
    <td>Implementa una lista doblemente enlazada para almacenar y gestionar usuarios. Proporciona métodos para buscar, crear, validar login y persistir datos en archivos .txt.</td>
  </tr>
  <tr>
    <td>Lista_Doble_Personajes.java</td>
    <td>Models.DataStructures</td>
    <td>Implementa una lista doblemente enlazada para almacenar y gestionar personajes de Sanrio. Carga personajes desde archivos Data.txt ubicados en las carpetas de cada personaje.</td>
  </tr>
  <tr>
    <td>Lista_Doble_Productos.java</td>
    <td>Models.DataStructures</td>
    <td>Implementa una lista doblemente enlazada para almacenar y gestionar productos. Carga productos desde archivos de texto ubicados en las carpetas de productos de cada personaje.</td>
  </tr>
  <tr>
    <td>Nodo_Usuario.java</td>
    <td>Models</td>
    <td>Implementa un nodo de la lista doblemente enlazada de usuarios. Contiene una referencia al objeto Usuario y punteros al nodo siguiente y anterior.</td>
  </tr>
  <tr>
    <td>Nodo_Producto.java</td>
    <td>Models</td>
    <td>Implementa un nodo de la lista doblemente enlazada de productos. Contiene una referencia al objeto Producto y punteros al nodo siguiente y anterior.</td>
  </tr>
  <tr>
    <td>Nodo_Personaje.java</td>
    <td>Models</td>
    <td>Implementa un nodo de la lista doblemente enlazada de personajes. Contiene una referencia al objeto Personaje y punteros al nodo siguiente y anterior.</td>
  </tr>
  <tr>
    <td>Usuario.java</td>
    <td>Models</td>
    <td>Clase modelo que representa un usuario del sistema. Contiene atributos: nombre, identificación, correo, contraseña y rol (administrador/cliente).</td>
  </tr>
  <tr>
    <td>Producto.java</td>
    <td>Models</td>
    <td>Clase modelo que representa un producto de Sanrio. Contiene atributos: personaje asociado, identificación, nombre, categorías, descripciones (inglés/español), precio, correo del usuario y fecha de compra.</td>
  </tr>
  <tr>
    <td>Personaje.java</td>
    <td>Models</td>
    <td>Clase modelo que representa un personaje de Sanrio. Contiene atributos: nombre, descripciones en inglés y español, y color asociado.</td>
  </tr>
  <tr>
    <td>Controller_Catalog_Section.java</td>
    <td>Controllers</td>
    <td>Controlador principal del catálogo. Gestiona la visualización de personajes y productos, filtrado, navegación a detalles, gestión de carrito y wishlist, y cambio de idioma.</td>
  </tr>
  <tr>
    <td>Controller_Login_and_Signing.java</td>
    <td>Controllers</td>
    <td>Controlador de la vista de login y registro. Gestiona la autenticación de usuarios, registro de nuevos usuarios, validaciones y el easter egg del modo administrador.</td>
  </tr>
  <tr>
    <td>Controller_Product_Detail_View.java</td>
    <td>Controllers</td>
    <td>Controlador de la vista de detalles de producto. Gestiona el carrusel de imágenes, selector de cantidad, productos recomendados y agregado al carrito.</td>
  </tr>
  <tr>
    <td>Controller_Checkout_View.java</td>
    <td>Controllers</td>
    <td>Controlador de la pasarela de pago. Gestiona la selección de método de pago, validación de campos, resumen de compra y finalización de la transacción.</td>
  </tr>
  <tr>
    <td>Controller_Cart_Panel.java</td>
    <td>Controllers</td>
    <td>Controlador del panel overlay del carrito de compras. Gestiona la visualización de productos en el carrito, eliminación, movimiento a wishlist y navegación al checkout.</td>
  </tr>
  <tr>
    <td>Controller_Wishlist_Panel.java</td>
    <td>Controllers</td>
    <td>Controlador del panel overlay de la wishlist. Gestiona la visualización de productos en la lista de deseos, eliminación y movimiento al carrito.</td>
  </tr>
  <tr>
    <td>Controller_Purchase_History_Panel.java</td>
    <td>Controllers</td>
    <td>Controlador del panel de historial de compras. Gestiona la visualización de compras realizadas en una tabla con información detallada de cada transacción.</td>
  </tr>
  <tr>
    <td>Controller_Change_Password_Panel.java</td>
    <td>Controllers</td>
    <td>Controlador del panel overlay de cambio de contraseña. Gestiona la validación y actualización de la contraseña del usuario.</td>
  </tr>
  <tr>
    <td>Component_ProductCard.java</td>
    <td>Controllers</td>
    <td>Controlador del componente reutilizable de tarjeta de producto. Gestiona la visualización de un producto individual en el catálogo con imagen, nombre, precio y botones de acción.</td>
  </tr>
  <tr>
    <td>Component_CharacterCard.java</td>
    <td>Controllers</td>
    <td>Controlador del componente reutilizable de tarjeta de personaje. Gestiona la visualización de un personaje individual con imagen y nombre para el filtrado del catálogo.</td>
  </tr>
  <tr>
    <td>Component_CartItem.java</td>
    <td>Controllers</td>
    <td>Controlador del componente reutilizable de item del carrito. Gestiona la visualización de un producto en el panel del carrito con opciones para eliminar o mover a wishlist.</td>
  </tr>
  <tr>
    <td>Component_WishlistItem.java</td>
    <td>Controllers</td>
    <td>Controlador del componente reutilizable de item de la wishlist. Gestiona la visualización de un producto en el panel de wishlist con opciones para eliminar o mover al carrito.</td>
  </tr>
  <tr>
    <td>Component_CheckoutItem.java</td>
    <td>Controllers</td>
    <td>Controlador del componente reutilizable de item del checkout. Gestiona la visualización de un producto en el resumen de compra con imagen, nombre, precio y cantidad.</td>
  </tr>
</table>

---

### Tabla de Vistas

<table align="center">
  <tr>
    <th colspan="3"><h3 align="center">Tabla de Vistas</h3></th>
  </tr>
  <tr>
    <th>Vista</th>
    <th>Controlador</th>
    <th>Descripción</th>
  </tr>
  <tr>
    <td>Login_and_Signing.fxml</td>
    <td>Controller_Login_and_Signing.java</td>
    <td>Vista principal de inicio de sesión y registro. Incluye formularios para login y registro, validaciones de campos, y elementos interactivos para el easter egg del modo administrador. El controlador gestiona la autenticación, registro de usuarios y navegación al catálogo.</td>
  </tr>
  <tr>
    <td>Catalog_Section.fxml</td>
    <td>Controller_Catalog_Section.java</td>
    <td>Vista principal del catálogo de productos. Incluye header con navegación, sección de personajes, cuadrícula de productos, footer y paneles overlay para wishlist, carrito y cambio de contraseña. El controlador gestiona el filtrado, carga incremental de productos, navegación y gestión de estado.</td>
  </tr>
  <tr>
    <td>Product_Detail_View.fxml</td>
    <td>Controller_Product_Detail_View.java</td>
    <td>Vista de detalles de un producto individual. Incluye carrusel de imágenes con thumbnails, información del producto, selector de cantidad, botón para agregar al carrito y sección de productos recomendados. El controlador gestiona la navegación de imágenes, selección de cantidad y agregado al carrito.</td>
  </tr>
  <tr>
    <td>Checkout_View.fxml</td>
    <td>Controller_Checkout_View.java</td>
    <td>Vista de la pasarela de pago (overlay). Incluye selección de método de pago (Tarjeta de crédito, Nequi, DaviPlata), campos dinámicos según el método seleccionado, resumen de productos y total de compra. El controlador gestiona la validación de campos, cálculo de totales y finalización de la compra.</td>
  </tr>
  <tr>
    <td>Cart_Panel.fxml</td>
    <td>Controller_Cart_Panel.java</td>
    <td>Panel overlay del carrito de compras. Incluye lista de productos en el carrito con cantidades, total de compra, botones para eliminar productos, mover a wishlist y botón de checkout. El controlador gestiona la carga de productos, actualización de precios y navegación al checkout.</td>
  </tr>
  <tr>
    <td>Wishlist_Panel.fxml</td>
    <td>Controller_Wishlist_Panel.java</td>
    <td>Panel overlay de la lista de deseos. Incluye lista de productos en la wishlist, botones para eliminar productos y mover al carrito. El controlador gestiona la carga de productos y las operaciones de eliminación y movimiento.</td>
  </tr>
  <tr>
    <td>Purchase_History_Panel.fxml</td>
    <td>Controller_Purchase_History_Panel.java</td>
    <td>Vista del historial de compras. Incluye banner lateral decorativo y tabla con columnas: número de producto, nombre, precio y correo del comprador. El controlador gestiona la carga de datos del historial y la visualización en la tabla.</td>
  </tr>
  <tr>
    <td>Change_Password_Panel.fxml</td>
    <td>Controller_Change_Password_Panel.java</td>
    <td>Panel overlay para cambio de contraseña. Incluye campos para contraseña actual, nueva contraseña y confirmación. El controlador gestiona la validación y actualización de la contraseña del usuario.</td>
  </tr>
  <tr>
    <td>ProductCard.fxml</td>
    <td>Component_ProductCard.java</td>
    <td>Componente reutilizable para mostrar una tarjeta de producto. Incluye imagen del producto, nombre, precio y botón "Add to Bag". El controlador gestiona la visualización de datos, eventos de clic en imagen y agregado al carrito.</td>
  </tr>
  <tr>
    <td>CharacterCard.fxml</td>
    <td>Component_CharacterCard.java</td>
    <td>Componente reutilizable para mostrar una tarjeta de personaje. Incluye imagen del personaje y nombre. El controlador gestiona la visualización y el evento de clic para filtrar productos por personaje.</td>
  </tr>
  <tr>
    <td>CartItem.fxml</td>
    <td>Component_CartItem.java</td>
    <td>Componente reutilizable para mostrar un item del carrito. Incluye imagen, nombre, precio, cantidad, botón de eliminación e icono para mover a wishlist. El controlador gestiona la visualización y las acciones de eliminación y movimiento.</td>
  </tr>
  <tr>
    <td>WishlistItem.fxml</td>
    <td>Component_WishlistItem.java</td>
    <td>Componente reutilizable para mostrar un item de la wishlist. Incluye imagen, nombre, precio, botón de eliminación e icono para mover al carrito. El controlador gestiona la visualización y las acciones de eliminación y movimiento.</td>
  </tr>
  <tr>
    <td>CheckoutItem.fxml</td>
    <td>Component_CheckoutItem.java</td>
    <td>Componente reutilizable para mostrar un item en el resumen de compra del checkout. Incluye imagen, nombre, precio unitario y cantidad. El controlador gestiona la visualización de datos y actualización de precios según el idioma.</td>
  </tr>
</table>
