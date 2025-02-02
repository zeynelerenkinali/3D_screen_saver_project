# Computer Graphics Project - Modern OpenGL with LWJGL

This project demonstrates the development of a 3D graphics application using **Modern OpenGL** and **LWJGL** (Lightweight Java Game Library). The application renders multiple 3D models, provides basic user interaction, and incorporates camera manipulation to navigate the scene. The primary goal is to showcase an understanding of rendering pipelines, object manipulation, and user interaction within a computer graphics context.

---
## Features

- **Window Management.**
  - Creates a window with a resolution of 1920x1080.
  - Supports fullscreen toggling using the **F11** key.
  - Background color is set dynamically.

- **3D Model Loading.**
  - Loads three distinct 3D models using the `ModelLoader` class.
  - Models include: `DVDVIDEO.obj`, `Cube.obj`, `ETU.obj`, and `Squirrel.obj`.
  - Each model is associated with a texture to enhance visual realism.

- **Rendering Pipeline.**
  - Renders each object using the `Renderer` class.
  - Utilizes a shader program for vertex and fragment processing.

- **Camera System.**
  - Supports static and dynamic camera modes.
  - Switch between modes using the **C** key.
  - Adjustable camera position and rotation for detailed scene viewing.

- **Object Manipulation.**
  - Objects can be individually manipulated or controlled collectively.
  - Switch between objects using the **T** key.
  - Toggle multi-object mode using the **M** key.

- **User Interaction.**
  - Integrates mouse and keyboard inputs for interaction.
  - Exit the application using the **ESC** key.
---

## Technical Implementation

### Main Class
The `Main` class serves as the entry point of the application and orchestrates the core functionalities:

- **Initialization.**
  - Sets up the window, shaders, models, and renderers.
  - Configures the camera and object properties.

- **Game Loop.**
  - Executes continuously until the window is closed or the escape key is pressed.
  - Handles updates (e.g., object motion, camera adjustments) and rendering.

- **Input Handling.**
  - Responds to key and mouse inputs for interaction.
---

### Dependencies

- **LWJGL.**
  - Provides the OpenGL bindings and window management.

- **Custom Classes.**
  - `Window`: Manages window creation and updates.
  - `Renderer`: Handles rendering of 3D objects.
  - `Shader`: Loads and compiles GLSL shader programs.
  - `Camera`: Implements static and dynamic camera modes.
  - `GameObject`: Encapsulates properties and behavior of 3D objects.
  - `Input`: Manages keyboard and mouse input states.
  - `ModelLoader`: Loads 3D models and textures from file.
---

## Challenges Faced

- **Model Loading.**
  - Ensuring compatibility between `.obj` files and texture files required debugging and validation.

- **Input Handling.**
  - Managing simultaneous key presses without conflicts was resolved using a state-based approach.

- **Camera Implementation.**
  - Balancing intuitive controls with flexibility required iterative adjustments.
---

## Potential Improvements

- **Lighting.**
  - Implement dynamic lighting to enhance visual realism.

- **Collision Detection.**
  - Introduce collision detection to prevent objects from overlapping.

- **UI Integration.**
  - Add an on-screen display for controls and object information.

- **Optimization.**
  - Optimize rendering for better performance on lower-end systems.
---

## Conclusion

This project successfully demonstrates the application of computer graphics concepts, including model rendering, camera manipulation, and user interaction. It serves as a foundation for more advanced graphics applications and highlights the potential of OpenGL in real-time rendering.

---
## How to Run

1. Clone the repository.
2. Ensure you have Java and LWJGL set up.
3. Compile and run the `Main` class.
4. Use the following controls:
   - **F11**: Toggle fullscreen.
   - **C**: Switch camera modes.
   - **T**: Switch between objects.
   - **M**: Toggle multi-object mode.
   - **ESC**: Exit the application.

---

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
