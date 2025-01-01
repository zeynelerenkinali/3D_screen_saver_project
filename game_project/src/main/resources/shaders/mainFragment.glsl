#version 460 core

in vec3 fragColor;               // Color passed from the vertex shader
in vec2 fragTextureCoord;        // Texture coordinates passed from vertex shader

out vec4 outColor;               // Final output color

uniform sampler2D tex;           // Texture uniform
uniform bool useTexture;         // A flag to enable/disable texture usage
uniform float someConstant;

void main() {
    vec4 texColor = texture(tex, fragTextureCoord); // Sample the texture

    // Check if texture should be used
    if (useTexture) {
        // Blend the texture with the fragment color (for example, averaging)
        outColor = mix(vec4(fragColor, 1.0), texColor, 0.5);  // Blending the color with texture
    } else {
        outColor = vec4(fragColor, 1.0);  // Use only the color if no texture is needed
    }
}
