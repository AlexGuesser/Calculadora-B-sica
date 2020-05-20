package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button botao0,botao1,botao2,botao3,botao4,botao5,botao6,botao7,botao8,botao9,botaoPonto,botaoSomar,botaoSubtrair,botaoDividir,botaoMultiplicar,botaoPorcentagem;
    private TextView textViewExpressao,textViewResultado;
    private ArrayList<String> expressaoList = new ArrayList<>();
    private String expressao = "";
    private String[] numerosArray = new String[]{"0","1","2","3","4","5","6","7","8","9","."};
    private String[] operadoresArray = new String[]{"-","+","x","/","."};
    private List<String> numeros = Arrays.asList(numerosArray);
    private List<String> operadores = Arrays.asList(operadoresArray);
    private List<Double> listaNumerosDoubles = new ArrayList<>();
    private List<String> listaOperacoes = new ArrayList<>();
    private int quantidadeDeCliques = 0;
    private String conteudoDoBotao;
    private String resultadoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurações iniciais utilizando findViewByID
        botao0 = findViewById(R.id.button0);
        botao1 = findViewById(R.id.button1);
        botao2 = findViewById(R.id.button2);
        botao3 = findViewById(R.id.button3);
        botao4 = findViewById(R.id.button4);
        botao5 = findViewById(R.id.button5);
        botao6 = findViewById(R.id.button6);
        botao7 = findViewById(R.id.button7);
        botao8 = findViewById(R.id.button8);
        botao9 = findViewById(R.id.button9);
        botaoSomar = findViewById(R.id.buttonSomar);
        botaoSubtrair = findViewById(R.id.buttonSubtrair);
        botaoMultiplicar = findViewById(R.id.buttonMultiplicar);
        botaoDividir = findViewById(R.id.buttonDividir);
        botaoPonto = findViewById(R.id.buttonPonto);

        textViewExpressao = findViewById(R.id.textViewExpressao);
        textViewResultado = findViewById(R.id.textViewDisplay);

    }

    public void onButtonClick(View view) {
        switch (view.getId()) {

            case R.id.button0:

                conteudoDoBotao = botao0.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button1:

                conteudoDoBotao = botao1.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button2:

                conteudoDoBotao = botao2.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button3:

                conteudoDoBotao = botao3.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button4:

                conteudoDoBotao = botao4.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button5:

                conteudoDoBotao = botao5.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;


            case R.id.button6:

                conteudoDoBotao = botao6.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button7:

                conteudoDoBotao = botao7.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button8:

                conteudoDoBotao = botao8.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.button9:

                conteudoDoBotao = botao9.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.buttonSomar:

                conteudoDoBotao = botaoSomar.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.buttonSubtrair:

                conteudoDoBotao = botaoSubtrair.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.buttonMultiplicar:

                conteudoDoBotao = botaoMultiplicar.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.buttonDividir:

                conteudoDoBotao = botaoDividir.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;

            case R.id.buttonPonto:

                conteudoDoBotao = botaoPonto.getText().toString();
                adicionaConteudoDoBotao(conteudoDoBotao);
                break;


            case R.id.buttonApagar:

                apagaUltimaInformacao();
                break;

            case R.id.buttonLimpar:

                limpaTudo();
                break;
        }
    }

    public void calculaResultado(View view){

        // Este if verifica se o usuário insiriu apenas um valor não numérico (uma operaçõa), se sim, limpa tudo e mostra o Toast
        if(apenasUmaOperacao()){
            limpaTudo();
            Toast.makeText(this, "Digite uma expressão matemática!", Toast.LENGTH_LONG).show();
        }else {
            recuperaNumeroseOperacoes(); //Gera os arrayList de números e operações
            retiraUltimaOperacao(); //Retira operações inseridas ao final da expressão matemática que viriam a gerar erros no app
            Boolean temDivisaoPorzero = validaDivisaoPorZero(); //Analisa se ocorre divisão por zero
            if (temDivisaoPorzero) {
                limpaTudo();
                Toast.makeText(this, "Divisão por zero! Por favor, refaça sua expressão", Toast.LENGTH_LONG).show();
            } else {

                if (expressaoComConteudo()) { //Analisa se com as correções acima ainda existe algum contéudo matematico para operar
                    realizaOperacoes(); //Realiza todas as operaços da expressão
                    mostraResultado(); //Mostra o resultado para o usuário e prepara os Arrays para uma operação seguinte
                } else {

                    Toast.makeText(this, "Digite uma expressão matemática!", Toast.LENGTH_LONG).show();

                }

            }
        }
    }

    public void recuperaNumeroseOperacoes(){



        String numero = "";

        for (int i =0;i<expressaoList.size();i++){

            String caracter = expressaoList.get(i);


            if(i == 0 && caracter.equals("+")){

                continue;
            }

            else if(i == 0 && caracter.equals("-")){

                numero = numero + caracter;
                continue;

            }

            else if(caracter.equals("-") && !numeros.contains(expressaoList.get(i-1))){

                    numero = numero + caracter;
                    continue;

            }

            else if(numeros.contains(caracter)){

                if(i!= expressaoList.size()-1){
                    numero = numero + caracter;
                    continue;
                }else{
                    numero = numero + caracter;
                    listaNumerosDoubles.add(Double.parseDouble(numero));
                    numero = "";
                    continue;

                }

            }

            else{

                listaNumerosDoubles.add(Double.parseDouble(numero));
                numero = "";
                listaOperacoes.add(caracter);
                continue;
            }


        }

        Log.i("Listas",listaNumerosDoubles.toString());
        Log.i("Listas",listaOperacoes.toString());


    }

    public void realizaOperacoes(){

        for (int i =0;i<listaOperacoes.size();i++){



            if(listaOperacoes.get(i).equals("x")){



               Double resultado = multiplica(i);
               adicionaResultadoCorrigeListas(i,resultado);
               i--;
               continue;


            }

            if(listaOperacoes.get(i).equals("/")){

                Double resultado = dividi(i);
                adicionaResultadoCorrigeListas(i,resultado);
                i--;
                continue;

            }

        }

        for (int i =0;i<listaOperacoes.size();i++){

            if(listaOperacoes.get(i).equals("+")){



                Double resultado = soma(i);
                adicionaResultadoCorrigeListas(i,resultado);
                i--;
                continue;


            }

            if(listaOperacoes.get(i).equals("-")){



                Double resultado = subtrai(i);
                adicionaResultadoCorrigeListas(i,resultado);
                i--;
                continue;

            }

        }


    }

    public Double multiplica(int i){

        Log.i("Listas",listaNumerosDoubles.toString());
        Log.i("Listas",listaOperacoes.toString());
        return (listaNumerosDoubles.get(i) * listaNumerosDoubles.get(i+1));



    }

    public Double dividi(int i){

        Log.i("Listas",listaNumerosDoubles.toString());
        Log.i("Listas",listaOperacoes.toString());
        return (listaNumerosDoubles.get(i) / listaNumerosDoubles.get(i+1));



    }

    public Double soma(int i){

        Log.i("Listas",listaNumerosDoubles.toString());
        Log.i("Listas",listaOperacoes.toString());
        return (listaNumerosDoubles.get(i) + listaNumerosDoubles.get(i+1));

    }

    public Double subtrai(int i){

        Log.i("Listas",listaNumerosDoubles.toString());
        Log.i("Listas",listaOperacoes.toString());
        return (listaNumerosDoubles.get(i) - listaNumerosDoubles.get(i+1));

    }


    public void mostraResultado(){

        Log.i("Listas",expressaoList.toString());
        resultadoFinal = listaNumerosDoubles.get(0).toString();
        textViewResultado.setText(resultadoFinal);
        expressao = "( " + expressao + " )";
        textViewExpressao.setText(expressao);
        expressaoList.clear();
        limpaListaNumerosDoubles();
        quantidadeDeCliques = 0;
        for (int i=0; i<resultadoFinal.length(); i++) {
            char c = resultadoFinal.charAt(i);
            expressaoList.add(String.valueOf(c));
        }
        Log.i("Listas",expressaoList.toString());


    }

    public void limpaListaNumerosDoubles(){

        listaNumerosDoubles.clear();

    }

    public void limpaTudo(){

        listaNumerosDoubles.clear();
        listaOperacoes.clear();
        expressaoList.clear();
        expressao = "";
        textViewResultado.setText(expressao);
        textViewExpressao.setText(expressao);


    }

    public void retiraUltimaOperacao(){

        for(int i=0; i<expressaoList.size(); i++){

            if(i== expressaoList.size()-1 && operadores.contains(expressaoList.get(i))){

                expressaoList.remove(i);
                listaOperacoes.remove(listaOperacoes.size()-1);
                expressao = expressao.substring(0, expressao.length() - 1);

            }


        }
    }

    public void adicionaConteudoDoBotao(String conteudo){

        expressao = expressao + conteudo;
        expressaoList.add(conteudo);
        textViewExpressao.setText(expressao);
        incrementaQuantidadeDeCliques();


    }

    public void incrementaQuantidadeDeCliques(){

        quantidadeDeCliques ++;

    }

    public void apagaUltimaInformacao(){

        if(quantidadeDeCliques > 0) {
            expressao = expressao.substring(0, expressao.length() - 1);
            textViewExpressao.setText(expressao);
            expressaoList.remove(expressaoList.size() - 1);
            quantidadeDeCliques--;
        }

    }

    public Boolean validaDivisaoPorZero(){

        if (listaOperacoes.contains("/")){

            for (int i=0;i<listaOperacoes.size();i++){

                if(listaNumerosDoubles.get(i+1) == 0.0 || listaNumerosDoubles.get(i+1).equals("")){

                    return true;

                }

            }
            return false;
        }

        return false;
    }

    public void adicionaResultadoCorrigeListas(int i,Double resultado){

        listaNumerosDoubles.add(i,resultado);
        listaNumerosDoubles.remove(i+1);
        listaNumerosDoubles.remove(i+1);
        listaOperacoes.remove(i);

    }

    public Boolean expressaoComConteudo(){

        if(expressaoList.size()==0){

            return false;

        }

        return true;
    }

    public Boolean apenasUmaOperacao(){

        if( quantidadeDeCliques==1 && operadores.contains(expressaoList.get(expressaoList.size()-1))){

            quantidadeDeCliques --;
            return true;

        }

        return false;
    }

}
