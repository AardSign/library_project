import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Typography,
  Box,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  MenuItem,
} from "@mui/material";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function EmployeeList() {
  const [employees, setEmployees] = useState([]);
  const [filteredEmployees, setFilteredEmployees] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [editDialogOpen, setEditDialogOpen] = useState(false);
  const [deleteConfirmDialogOpen, setDeleteConfirmDialogOpen] = useState(false);
  const [employeeToDelete, setEmployeeToDelete] = useState(null);

  // Determinar o tipo de usuário logado
  const userType = localStorage.getItem("userType");

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = async () => {
    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      const response = await axios.get("http://localhost:8080/api/funcionarios", {
        headers: {
          Authorization: `Basic ${basicAuth}`,
        },
      });
      setEmployees(response.data);
      setFilteredEmployees(response.data);
    } catch (error) {
      console.error("Erro ao buscar funcionários:", error);
      toast.error("Erro ao carregar a lista de funcionários.");
    }
  };

  const handleSearch = (e) => {
    const term = e.target.value.toLowerCase();
    setSearchTerm(term);
    const filtered = employees.filter(
      (employee) =>
        employee.nome.toLowerCase().includes(term) ||
        employee.cpf.includes(term)
    );
    setFilteredEmployees(filtered);
  };

  const handleDelete = async () => {
    if (userType !== "administrador") {
      toast.error("Ação não permitida. Apenas administradores podem deletar funcionários.");
      return;
    }

    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      await axios.delete(`http://localhost:8080/api/funcionarios/${employeeToDelete.cpf}`, {
        headers: {
          Authorization: `Basic ${basicAuth}`,
        },
      });
      toast.success("Funcionário deletado com sucesso!");
      fetchEmployees();
      setDeleteConfirmDialogOpen(false);
    } catch (error) {
      console.error("Erro ao deletar funcionário:", error);
      toast.error("Erro ao deletar funcionário.");
    }
  };

  const handleEditOpen = (employee) => {
    setSelectedEmployee(employee);
    setEditDialogOpen(true);
  };

  const handleEditClose = () => {
    setSelectedEmployee(null);
    setEditDialogOpen(false);
  };

  const handleEditSave = async () => {
    if (userType !== "administrador") {
      toast.error("Ação não permitida. Apenas administradores podem editar funcionários.");
      return;
    }

    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      await axios.put(
        `http://localhost:8080/api/funcionarios/${selectedEmployee.cpf}`,
        selectedEmployee,
        {
          headers: {
            Authorization: `Basic ${basicAuth}`,
            "Content-Type": "application/json",
          },
        }
      );
      toast.success("Funcionário atualizado com sucesso!");
      fetchEmployees();
      handleEditClose();
    } catch (error) {
      console.error("Erro ao atualizar funcionário:", error);
      toast.error("Erro ao atualizar funcionário.");
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSelectedEmployee((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleDeleteConfirmOpen = (employee) => {
    if (userType !== "administrador") {
      toast.error("Ação não permitida. Apenas administradores podem deletar funcionários.");
      return;
    }
    setEmployeeToDelete(employee);
    setDeleteConfirmDialogOpen(true);
  };

  const handleDeleteConfirmClose = () => {
    setEmployeeToDelete(null);
    setDeleteConfirmDialogOpen(false);
  };

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4" gutterBottom align="center">
        Lista de Funcionários
      </Typography>
      <Box sx={{ display: "flex", justifyContent: "flex-end", marginBottom: 2 }}>
        <TextField
          label="Pesquisar"
          variant="outlined"
          value={searchTerm}
          onChange={handleSearch}
          placeholder="Digite o nome ou CPF"
          fullWidth
          sx={{ maxWidth: 400 }}
        />
      </Box>
      <TableContainer component={Paper} sx={{ boxShadow: "0 0 15px rgba(0, 0, 0, 0.1)", backgroundColor: "#f9f9f9" }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>CPF</TableCell>
              <TableCell>Nome</TableCell>
              <TableCell>Telefone</TableCell>
              <TableCell>Endereço</TableCell>
              <TableCell>Data de Nascimento</TableCell>
              <TableCell>Função</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredEmployees.map((employee) => (
              <TableRow key={employee.cpf}>
                <TableCell>{employee.cpf}</TableCell>
                <TableCell>{employee.nome}</TableCell>
                <TableCell>{employee.telefone}</TableCell>
                <TableCell>{employee.endereco}</TableCell>
                <TableCell>{employee.dataNascimento}</TableCell>
                <TableCell>{employee.funcao}</TableCell>
                <TableCell>{employee.tipo}</TableCell>
                <TableCell>
                  <Box sx={{ display: "flex", gap: 1 }}>
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleEditOpen(employee)}
                      disabled={userType !== "administrador"}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDeleteConfirmOpen(employee)}
                      disabled={userType !== "administrador"}
                    >
                      Deletar
                    </Button>
                  </Box>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={deleteConfirmDialogOpen} onClose={handleDeleteConfirmClose}>
        <DialogTitle>Confirmar Exclusão</DialogTitle>
        <DialogContent>
          Tem certeza que deseja excluir o funcionário{" "}
          <strong>{employeeToDelete?.nome}</strong>?
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteConfirmClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleDelete} color="error">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog open={editDialogOpen} onClose={handleEditClose}>
        <DialogTitle>Editar Funcionário</DialogTitle>
        <DialogContent>
          <TextField
            label="Nome"
            name="nome"
            fullWidth
            margin="normal"
            value={selectedEmployee?.nome || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Telefone"
            name="telefone"
            fullWidth
            margin="normal"
            value={selectedEmployee?.telefone || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Endereço"
            name="endereco"
            fullWidth
            margin="normal"
            value={selectedEmployee?.endereco || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Data de Nascimento"
            name="dataNascimento"
            type="date"
            fullWidth
            margin="normal"
            value={selectedEmployee?.dataNascimento || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Função"
            name="funcao"
            fullWidth
            margin="normal"
            value={selectedEmployee?.funcao || ""}
            onChange={handleInputChange}
          />
          <TextField
            select
            label="Tipo"
            name="tipo"
            fullWidth
            margin="normal"
            value={selectedEmployee?.tipo || ""}
            onChange={handleInputChange}
          >
            <MenuItem value="administrador">Administrador</MenuItem>
            <MenuItem value="comum">Funcionário Comum</MenuItem>
          </TextField>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleEditClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleEditSave} color="primary">
            Salvar
          </Button>
        </DialogActions>
      </Dialog>

      <ToastContainer />
    </Box>
  );
}

export default EmployeeList;
