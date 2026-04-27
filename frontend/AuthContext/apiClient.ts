import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
});

apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        const status = error.response?.status

        if (status === 401){
            localStorage.removeItem("token")
            window.location.href = '/login'
        }

        if (status == 403){
            window.location.href = '/forbidden'
        }

        return Promise.reject(error)
    }
)

export default apiClient;