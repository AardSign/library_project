package br.com.biblioteca.app;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import br.com.biblioteca.modelos.Cliente;
import dao.ClienteDAO;

@WebServlet("/listarClientes")
public class ListarClientesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            ClienteDAO clienteDAO = new ClienteDAO(connection);

            List<Cliente> clientes = clienteDAO.listarClientes();
            request.setAttribute("clientes", clientes);

            RequestDispatcher dispatcher = request.getRequestDispatcher("listarClientes.jsp");
            dispatcher.forward(request, response);

            connection.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
    }
}



