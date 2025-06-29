import api from '../axiosConfig'
import type { Product } from '../types/Product';

export async function fetchProducts(): Promise<Product[]> {
  const response = await api.get('/api/products/allProducts');
  return response.data;
}