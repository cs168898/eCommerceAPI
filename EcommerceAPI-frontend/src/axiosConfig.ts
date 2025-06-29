import axios from 'axios';

export default axios.create({
    // for those testing this code, replace with your own backend URL
  baseURL: 'http://localhost:8080',
});