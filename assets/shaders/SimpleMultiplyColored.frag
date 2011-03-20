#import "Common/ShaderLib/Texture.glsllib"

varying vec2 texCoord;

uniform sampler2D m_ColorMap;
uniform vec4 m_Col;

void main(){
    gl_FragColor = Texture_GetColor(m_ColorMap, texCoord);
    gl_FragColor *= m_Col;
}