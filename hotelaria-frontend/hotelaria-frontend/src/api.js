import axios from 'axios';
// O proxy do Vite redireciona /api para http://localhost:8080 no Codespaces.
export const api = axios.create({baseURL:'/api',timeout:12000});
export function mensagemErro(erro){const dados=erro.response?.data;return (typeof dados==='string'&&dados)||(dados?.message)||(dados?.detail)||(erro.response?`Erro HTTP ${erro.response.status}`:'Não foi possível conectar ao backend. Verifique se o Spring Boot está ligado na porta 8080.');}
