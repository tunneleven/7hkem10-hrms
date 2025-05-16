import axios from 'axios';
import { employeeList } from "../data/mockData";

// API URL configuration
const API_URL = 'https://33d2-52-77-212-138.ngrok-free.app';

// Helper function to get token from localStorage
const getToken = () => {
  return localStorage.getItem('token');
};

// Create axios instance with default headers
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'accept': 'application/json',
    'ngrok-skip-browser-warning': 'skip-browser-warning',
    'Content-Type': 'application/json',
  }
});

// Add token to all requests if it exists
api.interceptors.request.use((config) => {
  const token = getToken();
  console.log('Current token:', token); // Debug log
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
    console.log('Request headers:', config.headers); // Debug log
  }
  return config;
});

// Helper function to map API data with mock data
const mapEmployeeData = (apiData) => {
  return apiData.map(user => {
    // Find matching mock data or use first mock as default
    const mockData = employeeList.find(mock => mock.id === user.id) || employeeList[0];

    return {
      ...mockData, // Start with all mock data
      id: user.id,
      name: user.name,
      username: user.username,
      role: user.role,
      // Map other fields from mock data but keep API data as priority
      email: mockData.email,
      workPhone: mockData.workPhone,
      gender: mockData.gender,
      status: mockData.status,
      dateJoin: mockData.dateJoin,
      dateLeft: mockData.dateLeft,
      country: mockData.country,
      idNumber: mockData.idNumber,
      idDate: mockData.idDate,
      idAddress: mockData.idAddress,
      birthday: mockData.birthday,
      placeOfBirth: mockData.placeOfBirth,
      permanentAddress: mockData.permanentAddress,
      currentAddress: mockData.currentAddress,
      passport: mockData.passport,
      maritalStatus: mockData.maritalStatus,
      emergencyContact: mockData.emergencyContact,
      emergencyPhone: mockData.emergencyPhone,
      socialInsuranceCode: mockData.socialInsuranceCode,
      taxCode: mockData.taxCode,
      religion: mockData.religion,
      company: mockData.company,
      department: mockData.department,
      jobPosition: mockData.jobPosition,
      jobTitle: mockData.jobTitle,
      province: mockData.province,
      district: mockData.district,
      ward: mockData.ward,
      user: user.id, // Map to API user id
      manager: mockData.manager,
      isUnion: mockData.isUnion
    };
  });
};

// Helper function to get headers
const getHeaders = (method = 'GET', body = null) => {
  const headers = {
    'Authorization': `Bearer ${localStorage.getItem('token')}`,
    'Content-Type': 'application/json',
    'ngrok-skip-browser-warning': 'skip-browser-warning'
  };

  return {
    method,
    headers,
    ...(body && { body: JSON.stringify(body) })
  };
};

// Helper function for API calls
const apiCall = async (endpoint, options = {}) => {
  const response = await fetch(`${API_URL}${endpoint}`, options);
  if (!response.ok) {
    const errorText = await response.text();
    console.error('API Error:', errorText);
    throw new Error(`API call failed: ${endpoint}`);
  }
  // For DELETE requests or empty responses, return null instead of trying to parse JSON
  if (options.method === 'DELETE' || response.status === 204) {
    return null;
  }
  return response.json();
};

// Example API methods
export const apiService = {
  // Get users
  getUsers: async () => {
    return apiCall('/api/resUsers', getHeaders());
  },

  // GET request example
  getData: async () => {
    try {
      const response = await api.get('/your-endpoint');
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      throw error;
    }
  },

  // POST request example
  postData: async (data) => {
    try {
      const response = await api.post('/your-endpoint', data);
      return response.data;
    } catch (error) {
      console.error('Error posting data:', error);
      throw error;
    }
  },

  // Location APIs
  async getProvinces() {
    return apiCall('/api/resProvinces', getHeaders());
  },

  async getDistricts() {
    return apiCall('/api/resDistricts', getHeaders());
  },

  async getDistrictWards() {
    return apiCall('/api/resDistrictWards', getHeaders());
  },

  async getLocations() {
    return apiCall('/api/resLocations', getHeaders());
  },

  // Company APIs
  async getCompanies() {
    return apiCall('/api/resCompanies', getHeaders());
  },

  // Department APIs
  async getDepartments() {
    return apiCall('/api/hrDepartments', getHeaders());
  },

  async getDepartment(id) {
    return apiCall(`/api/hrDepartments/${id}`, getHeaders());
  },

  async createDepartment(data) {
    return apiCall('/api/hrDepartments', getHeaders('POST', data));
  },

  async updateDepartment(id, data) {
    return apiCall(`/api/hrDepartments/${id}`, getHeaders('PUT', data));
  },

  async deleteDepartment(id) {
    return apiCall(`/api/hrDepartments/${id}`, getHeaders('DELETE'));
  },

  // Job Position APIs
  async getJobPositions() {
    return apiCall('/api/hrJobPositions', getHeaders());
  },

  async getJobPosition(id) {
    return apiCall(`/api/hrJobPositions/${id}`, getHeaders());
  },

  async createJobPosition(data) {
    return apiCall('/api/hrJobPositions', getHeaders('POST', data));
  },

  async updateJobPosition(id, data) {
    return apiCall(`/api/hrJobPositions/${id}`, getHeaders('PUT', data));
  },

  async deleteJobPosition(id) {
    return apiCall(`/api/hrJobPositions/${id}`, getHeaders('DELETE'));
  },

  // Auth APIs
  signIn: async (username, password) => {
    try {
      const response = await fetch(`${API_URL}/api/auth/sign_in`, {
        method: 'POST',
        headers: {
          'accept': 'application/json',
          'Content-Type': 'application/json',
          'ngrok-skip-browser-warning': 'skip-browser-warning'
        },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        throw new Error('Failed to sign in');
      }

      const token = await response.text();
      if (token) {
        localStorage.setItem('token', token);
        localStorage.setItem('username', username);

        // Fetch and store location, company, and department data
        try {
          const [provinces, districts, districtWards, locations, companies, departments] = await Promise.all([
            apiService.getProvinces(),
            apiService.getDistricts(),
            apiService.getDistrictWards(),
            apiService.getLocations(),
            apiService.getCompanies(),
            apiService.getDepartments()
          ]);

          localStorage.setItem('provinces', JSON.stringify(provinces));
          localStorage.setItem('districts', JSON.stringify(districts));
          localStorage.setItem('districtWards', JSON.stringify(districtWards));
          localStorage.setItem('locations', JSON.stringify(locations));
          localStorage.setItem('companies', JSON.stringify(companies));
          localStorage.setItem('departments', JSON.stringify(departments));
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      }
      return token;
    } catch (error) {
      console.error('Error signing in:', error);
      throw error;
    }
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  },

  // Job Title APIs
  async getJobTitles() {
    return apiCall('/api/hrJobTitles', getHeaders());
  },

  async getJobTitle(id) {
    return apiCall(`/api/hrJobTitles/${id}`, getHeaders());
  },

  async createJobTitle(data) {
    return apiCall('/api/hrJobTitles', getHeaders('POST', data));
  },

  async updateJobTitle(id, data) {
    return apiCall(`/api/hrJobTitles/${id}`, getHeaders('PUT', data));
  },

  async deleteJobTitle(id) {
    return apiCall(`/api/hrJobTitles/${id}`, getHeaders('DELETE'));
  },

  // Contract Type APIs
  async getContractTypes() {
    return apiCall('/api/hrContractTypes', getHeaders());
  },

  async getContractType(id) {
    return apiCall(`/api/hrContractTypes/${id}`, getHeaders());
  },

  async createContractType(data) {
    return apiCall('/api/hrContractTypes', getHeaders('POST', data));
  },

  async updateContractType(id, data) {
    return apiCall(`/api/hrContractTypes/${id}`, getHeaders('PUT', data));
  },

  async deleteContractType(id) {
    return apiCall(`/api/hrContractTypes/${id}`, getHeaders('DELETE'));
  },

  // Contract APIs
  async getContracts() {
    return apiCall('/api/hrContracts', getHeaders());
  },

  async getContract(id) {
    return apiCall(`/api/hrContracts/${id}`, getHeaders());
  },

  async createContract(data) {
    return apiCall('/api/hrContracts', getHeaders('POST', data));
  },

  async updateContract(id, data) {
    return apiCall(`/api/hrContracts/${id}`, getHeaders('PUT', data));
  },

  async deleteContract(id) {
    return apiCall(`/api/hrContracts/${id}`, getHeaders('DELETE'));
  },

  // Employee APIs
  async getEmployees() {
    return apiCall('/api/hrEmployees', getHeaders());
  },

  async getEmployee(id) {
    return apiCall(`/api/hrEmployees/${id}`, getHeaders());
  },

  async createEmployee(data) {
    return apiCall('/api/hrEmployees', getHeaders('POST', data));
  },

  async updateEmployee(id, data) {
    return apiCall(`/api/hrEmployees/${id}`, getHeaders('PUT', data));
  },

  async deleteEmployee(id) {
    return apiCall(`/api/hrEmployees/${id}`, getHeaders('DELETE'));
  },

  // Role APIs
  async getUserRoles() {
    return apiCall('/api/resUserRoles', getHeaders());
  },

  async getRoles() {
    return apiCall('/api/resUserRoles', getHeaders());
  },

  async createRole(roleData) {
    return apiCall('/api/resUserRoles', getHeaders('POST', roleData));
  },

  async deleteRole(roleId) {
    return apiCall(`/api/resUserRoles/${roleId}`, getHeaders('DELETE'));
  },

  async updateRole(roleId, roleData) {
    return apiCall(`/api/resUserRoles/${roleId}`, getHeaders('PUT', roleData));
  },

  // User APIs
  async createUser(userData) {
    const requestBody = {
      id: 9007199254740991, // Using max safe integer as default
      username: userData.username,
      password: userData.password,
      name: userData.name,
      role: parseInt(userData.role)
    };
    return apiCall('/api/resUsers', getHeaders('POST', requestBody));
  },

  async updateUser(id, userData) {
    const requestBody = {
      id: parseInt(id),
      username: userData.username,
      password: userData.password || '', // Optional password field
      name: userData.name,
      role: parseInt(userData.role)
    };
    return apiCall(`/api/resUsers/${id}`, getHeaders('PUT', requestBody));
  },

  async deleteUser(id) {
    return apiCall(`/api/resUsers/${id}`, getHeaders('DELETE'));
  },
};