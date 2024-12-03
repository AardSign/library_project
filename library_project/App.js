import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import RegisterEmployee from './pages/RegisterEmployee';
import RegisterClient from './pages/RegisterClient';
import RegisterBooks from './pages/RegisterBooks';
import EmployeeList from './pages/EmployeeList';
import ClientList from './pages/ClientList';
import BookList from './pages/BookList';
import LoanManagement from './pages/LoanManagement';
import MainPage from "./pages/MainPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register-employee" element={<RegisterEmployee />} />
        <Route path="/register-client" element={<RegisterClient />} />
        <Route path="/register-books" element={<RegisterBooks />} />
        <Route path="/employee-List" element={<EmployeeList />} />
        <Route path="/client-List" element={<ClientList/>} />
        <Route path="/book-List" element={<BookList />} />
        <Route path="/loan-management" element={<LoanManagement />} />
        <Route path="/main-page" element={<MainPage />} />
      </Routes>
    </Router>
  );
}

export default App;
