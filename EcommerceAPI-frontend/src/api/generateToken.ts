import api from '../axiosConfig'

export async function generateToken(email: string, password: string): Promise<string> {
  const response = await api.post('/api/user/generateToken', {
    email,
    password
  });
  return response.data;
}