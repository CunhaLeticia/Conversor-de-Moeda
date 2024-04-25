import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        try {
            String url_str = "https://v6.exchangerate-api.com/v6/c0a030698e82c903a486a9d6/latest/USD";
            Scanner sc = new Scanner(System.in);

            System.out.println("\nSeja bem-vindo/a ao Conversor de Moeda x)\n");
            System.out.println("1) Dólar =>> Peso Argentino\n" +
                    "2) Peso Argetino =>> Dólar\n" +
                    "3) Dólar =>> Real Brasileiro\n" +
                    "4) Real Brasileiro =>> Dólar\n" +
                    "5) Dólar =>> Peso Colombiano\n" +
                    "6) Peso Colombiano =>> Dólar\n" +
                    "7) Dólar ==> Euro\n" +
                    "8) Sair");


            int escolheUmAeFulano = sc.nextInt();
            double numeroEntrada = sc.nextDouble();


            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            if (jsonobj.get("result").getAsString().equals("success")) {
                JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");

                double brlRate = conversionRates.get("BRL").getAsDouble();
                double eurRate = conversionRates.get("EUR").getAsDouble();
                double pesoArgentino = conversionRates.get("ARS").getAsDouble();
                double usdRate = conversionRates.get("USD").getAsDouble();
                double copRate = conversionRates.get("COP").getAsDouble();


                switch (escolheUmAeFulano){
                    case 1:
                        System.out.println("Valor " + numeroEntrada + " [USD] corresponde ao valor final de =>>> " + pesoArgentino*numeroEntrada +  " [ARS]");
                        break;
                    case 2:
                        System.out.println("Valor " + numeroEntrada + " [ARS] corresponde ao valor final de =>>> " + (numeroEntrada/pesoArgentino)*usdRate +  " [USD]");
                        break;
                    case 3:
                        System.out.println("Valor " + numeroEntrada + " [USD] corresponde ao valor final de =>>> " + brlRate*numeroEntrada +  " [BRL]");
                        break;
                    case 4:
                        System.out.println("Valor " + numeroEntrada + " [BRL] corresponde ao valor final de =>>> " + (numeroEntrada/brlRate) + usdRate + " [USD]");
                        break;
                    case 5:
                        System.out.println("Valor " + numeroEntrada + " [USD] corresponde ao valor final de =>>> " + copRate*numeroEntrada +  " [COP]");
                        break;
                    case 6:
                        System.out.println("Valor " + numeroEntrada + " [COP] corresponde ao valor final de =>>> " + (numeroEntrada/copRate)*usdRate  +  " [USD]");
                        break;
                    case 7:
                        System.out.println("Valor " + numeroEntrada + " [USD] corresponde ao valor final de =>>> " + (numeroEntrada/eurRate) +  " [EUR]");
                        break;
                    default:
                        System.out.println("Você não digitou nenhuma opção válida xP");
                        break;
                }

                System.out.println();
                System.out.println("Taxa de câmbio de USD para BRL: " + brlRate);
                System.out.println("Taxa de câmbio de USD para EUR: " + eurRate);
                System.out.println("Taxa de câmbio de USD para ARS: " + pesoArgentino);
                System.out.println("Taxa de câmbio de USD para USD: " + usdRate);
                System.out.println("Taxa de câmbio de USD para COP: " + copRate);

            } else {
                System.out.println("A solicitação não foi bem-sucedida");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
