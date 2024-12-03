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

function ClientList() {
  const [clients, setClients] = useState([]);
  const [filteredClients, setFilteredClients] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedClient, setSelectedClient] = useState(null);
  const [editDialogOpen, setEditDialogOpen] = useState(false);
  const [deleteConfirmDialogOpen, setDeleteConfirmDialogOpen] = useState(false);
  const [clientToDelete, setClientToDelete] = useState(null);

  const preferences = [
"Romance",
"Fantasia",
"Aventura",
"Terror",
"Suspense",
"Mistério",
"Drama",
"Biografia",
"Autobiografia",
"História",
"História em Quadrinhos",
"Poesia",
"Ensaios",
"Literatura Clássica",
"Literatura Contemporânea",
"Policial",
"Jovem Adulto",
"Infantil",
"Didático",
"Autoajuda",
"Psicologia",
"Filosofia",
"Religioso",
"Espiritualidade",
"Humor",
"Negócios",
"Tecnologia",
"Ciência",
"Culinária",
"Viagem",
"Distopia",
"Realismo Mágico",
"Épico",
"Erótico"
  ];

  const fetchClients = async () => {
    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      const response = await axios.get("http://localhost:8080/api/clientes", {
        headers: {
          Authorization: `Basic ${basicAuth}`,
        },
      });
      setClients(response.data);
      setFilteredClients(response.data);
    } catch (error) {
      console.error("Erro ao buscar clientes:", error);
      toast.error("Erro ao carregar a lista de clientes.");
    }
  };

  const handleSearch = (e) => {
    const term = e.target.value.toLowerCase();
    setSearchTerm(term);
    const filtered = clients.filter(
      (client) =>
        client.nome.toLowerCase().includes(term) || client.cpf.includes(term)
    );
    setFilteredClients(filtered);
  };

  const handleDelete = async () => {
    try {
      const userType = localStorage.getItem("userType");
      if (userType !== "administrador") {
        toast.error("Ação não permitida. Apenas administradores podem deletar clientes.");
        return;
      }

      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      await axios.delete(
        `http://localhost:8080/api/clientes/cpf/${clientToDelete.cpf}`,
        {
          headers: {
            Authorization: `Basic ${basicAuth}`,
          },
        }
      );
      toast.success("Cliente deletado com sucesso!");
      fetchClients();
      setDeleteConfirmDialogOpen(false);
    } catch (error) {
      console.error("Erro ao deletar cliente:", error.response || error);
      toast.error("Erro ao deletar cliente.");
    }
  };

  const handleEditSave = async () => {
    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      await axios.put(
        `http://localhost:8080/api/clientes/cpf/${selectedClient.cpf}`,
        selectedClient,
        {
          headers: {
            Authorization: `Basic ${basicAuth}`,
            "Content-Type": "application/json",
          },
        }
      );
      toast.success("Cliente atualizado com sucesso!");
      fetchClients();
      handleEditClose();
    } catch (error) {
      console.error("Erro ao atualizar cliente:", error.response || error);
      toast.error("Erro ao atualizar cliente.");
    }
  };

  const handleEditOpen = (client) => {
    setSelectedClient(client);
    setEditDialogOpen(true);
  };

  const handleEditClose = () => {
    setSelectedClient(null);
    setEditDialogOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSelectedClient((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleDeleteConfirmOpen = (client) => {
    setClientToDelete(client);
    setDeleteConfirmDialogOpen(true);
  };

  const handleDeleteConfirmClose = () => {
    setClientToDelete(null);
    setDeleteConfirmDialogOpen(false);
  };

  useEffect(() => {
    fetchClients();
  }, []);

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4" gutterBottom align="center">
        Lista de Clientes
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
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>CPF</TableCell>
              <TableCell>Nome</TableCell>
              <TableCell>Telefone</TableCell>
              <TableCell>Endereço</TableCell>
              <TableCell>Preferências</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredClients.map((client) => (
              <TableRow key={client.cpf}>
                <TableCell>{client.cpf}</TableCell>
                <TableCell>{client.nome}</TableCell>
                <TableCell>{client.telefone}</TableCell>
                <TableCell>{client.endereco}</TableCell>
                <TableCell>{client.preferencias}</TableCell>
                <TableCell>
                  <Box sx={{ display: "flex", gap: 1 }}>
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleEditOpen(client)}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDeleteConfirmOpen(client)}
                      disabled={localStorage.getItem("userType") !== "administrador"}
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

      {/* Dialog para Confirmar Exclusão */}
      <Dialog open={deleteConfirmDialogOpen} onClose={handleDeleteConfirmClose}>
        <DialogTitle>Confirmar Exclusão</DialogTitle>
        <DialogContent>
          Tem certeza que deseja excluir o cliente{" "}
          <strong>{clientToDelete?.nome}</strong>?
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

      {/* Dialog para Editar Cliente */}
      <Dialog open={editDialogOpen} onClose={handleEditClose}>
        <DialogTitle>Editar Cliente</DialogTitle>
        <DialogContent>
          <TextField
            label="Nome"
            name="nome"
            fullWidth
            margin="normal"
            value={selectedClient?.nome || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Telefone"
            name="telefone"
            fullWidth
            margin="normal"
            value={selectedClient?.telefone || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Endereço"
            name="endereco"
            fullWidth
            margin="normal"
            value={selectedClient?.endereco || ""}
            onChange={handleInputChange}
          />
          <TextField
            select
            label="Preferências"
            name="preferencias"
            fullWidth
            margin="normal"
            value={selectedClient?.preferencias || ""}
            onChange={handleInputChange}
          >
            {preferences.map((preference) => (
              <MenuItem key={preference} value={preference}>
                {preference}
              </MenuItem>
            ))}
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

export default ClientList;
