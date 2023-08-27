package com.example.fxcurrencyconverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class CurrencyController implements Initializable {

    @FXML
    private TextField amount;

    @FXML
    private ChoiceBox<String> fromCurrency, toCurrency;

    @FXML
    private Label results;
    private final Set<String> currencyCodes= CurrencyClass.currencyCodes();
    private String fromRate;
    private String toRate;

    @FXML
    private void onClick() {

        String lastUpdate = CurrencyClass.ratesRecord("USD").time_last_update_utc();
        String nextUpdate = CurrencyClass.ratesRecord("USD").time_next_update_utc();
        String message = """
                Result: * ^ = $ @
                
                Last update Time: #
                Next update Time: !""";
        try{
            double userAmount = Double.parseDouble(amount.getText());
            String convertedAmount = CurrencyClass.rateConversion(fromRate,userAmount,toRate);
            results.setText(message.replace("*",String.valueOf(userAmount))
                    .replace("^",fromRate)
                    .replace("$", convertedAmount)
                    .replace("@",toRate)
                    .replace("#", lastUpdate)
                    .replace("!", nextUpdate));
        } catch (NumberFormatException e){
            popupBox();
        }
    }
    private void getFrom(ActionEvent actionEvent){
        fromRate = fromCurrency.getValue();

    }

    private void getTo(ActionEvent actionEvent){
        toRate = toCurrency.getValue();
    }

    private void popupBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error message");
        alert.setHeaderText("Number format should be x.xx");
        alert.setContentText("dot \".\" deliminator instead of comma deliminator");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromCurrency.getItems().addAll(currencyCodes);
        toCurrency.getItems().addAll(currencyCodes);

        fromCurrency.setOnAction(this::getFrom);
        toCurrency.setOnAction(this::getTo);

        fromCurrency.setValue("USD");
        toCurrency.setValue("ZAR");
    }

}