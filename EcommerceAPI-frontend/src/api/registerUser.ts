import api from '../axiosConfig'

export async function registerUser(username: string, email: string, password: string): Promise<string> {
  const response = await api.post('/api/user/register', {
    username,
    email,
    password
  });
  return response.data;
}