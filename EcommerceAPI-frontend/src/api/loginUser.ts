import api from '../axiosConfig'
import type { User } from '../types/User';

type LoginResponse = {
  success: boolean;
  message: string;
  data: User;
};

export async function loginUser(email: string, password: string): Promise<LoginResponse> {
  const response = await api.post('/api/user/login', {
    email,
    password
  });
  return response.data;
}