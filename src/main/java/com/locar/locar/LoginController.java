package com.locar.locar;

import com.locar.locar.ExeExecutor.ExecutionResult;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private TextField senha;

    public void CriarConta(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cadastro.fxml"));
            Parent telaPrincipalRoot = fxmlLoader.load();

            Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAtual.getScene().setRoot(telaPrincipalRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TelaInicial(javafx.event.ActionEvent event) {
        try {
            ExecutionResult result = ExeExecutor.executeExe("api_bundle.exe",
                    new String[] { "getEmployee", email.getText(), senha.getText() });

            if (!result.stderr.isEmpty()) {
                throw new Exception(result.stderr);
            }

            System.out.println(result.stdout);

            HelloApplication.id_cadastroFuncionario = Integer
                    .parseInt(result.stdout.split("\n")[0].split(":")[1].trim());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));
            Parent telaPrincipalRoot = fxmlLoader.load();

            Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAtual.getScene().setRoot(telaPrincipalRoot);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Erro! Entre em contato com o suporte");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
