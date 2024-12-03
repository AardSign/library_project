import React from "react";
import { useNavigate } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Typography,
  Box,
  Button,
  List,
  ListItem,
  ListItemText,
  Drawer,
  CssBaseline,
} from "@mui/material";

const drawerWidth = 240;

const MainPage = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{
          width: `calc(100% - ${drawerWidth}px)`,
          ml: `${drawerWidth}px`,
          background: "linear-gradient(135deg, #333, #555)",
        }}
      >
        <Toolbar>
          <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ fontWeight: "bold", flexGrow: 1 }}
          >
            Biblioteca - Página Principal
          </Typography>
          <Button
            color="inherit"
            onClick={handleLogout}
            sx={{
              border: "1px solid #fff",
              padding: "5px 15px",
              borderRadius: "8px",
              "&:hover": { background: "#777" },
            }}
          >
            Sair
          </Button>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: {
            width: drawerWidth,
            boxSizing: "border-box",
            background: "linear-gradient(135deg, #555, #777)",
            color: "#fff",
          },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: "auto" }}>
          <List>
            <ListItem button onClick={() => handleNavigation("/register-employee")}>
              <ListItemText primary="Registrar Funcionário" />
            </ListItem>
            <ListItem button onClick={() => handleNavigation("/register-client")}>
              <ListItemText primary="Registrar Cliente" />
            </ListItem>
            <ListItem button onClick={() => handleNavigation("/register-books")}>
              <ListItemText primary="Registrar Livro" />
            </ListItem>
            <ListItem button onClick={() => handleNavigation("/employee-List")}>
              <ListItemText primary="Gerenciar Funcionários" />
            </ListItem>
            <ListItem button onClick={() => handleNavigation("/client-List")}>
              <ListItemText primary="Gerenciar Clientes" />
            </ListItem>
            <ListItem button onClick={() => handleNavigation("/book-List")}>
              <ListItemText primary="Gerenciar Livros" />
            </ListItem>
            <ListItem button onClick={() => handleNavigation("/loan-management")}>
              <ListItemText primary="Gerenciar Empréstimos" />
            </ListItem>
          </List>
        </Box>
      </Drawer>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          bgcolor: "#f5f5f5",
          p: 3,
          height: "100vh",
          background: "linear-gradient(135deg, #e0e0e0, #ffffff)",
        }}
      >
        <Toolbar />
        <Typography variant="h4" gutterBottom>
          Bem-vindo à Biblioteca!
        </Typography>
        <Typography variant="body1">
          Utilize o menu lateral para navegar pelas funcionalidades do sistema.
        </Typography>
      </Box>
    </Box>
  );
};

export default MainPage;
