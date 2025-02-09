package org.willian;

import org.willian.application.App;
import org.willian.domain.entities.Funcionario;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private static DateTimeFormatter dateFormatter = null;
    private static DecimalFormat decimalFormat = null;

    static {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        decimalFormat = new DecimalFormat("#,##0.00", symbols);
    }

    public static void imprimirListaFuncionarios(List<Funcionario> funcionarios, String titulo) {
        System.out.println(titulo);
        funcionarios.forEach(funcionario -> System.out.println(
                "Nome: " + funcionario.getNome() +
                        " | Data de Nascimento: " + funcionario.getDataNascimento().format(dateFormatter) +
                        " | Salário: R$ " + decimalFormat.format(funcionario.getSalario()) +
                        " | Função: " + funcionario.getFuncao()
        ));
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("data.json");

        App app = new App();
        List<Funcionario> funcionarios;

        System.out.println("Adicionando Funcionários...");
        funcionarios = app.criarListaFuncionarios(inputStream);
        imprimirListaFuncionarios(funcionarios, "Lista de Funcionários:");

        System.out.println("Removendo Funcionário João...");
        funcionarios = app.removerFuncionario("João");
        imprimirListaFuncionarios(funcionarios, "Lista de Funcionários:");

        System.out.println("Adicionando aumento de salarios...");
        funcionarios = app.adicionarAumentoDeSalario(1.10);
        System.out.println("Salários atualizados com 10% de aumento:");
        funcionarios.forEach(funcionario -> System.out.println(
                "Nome: " + funcionario.getNome() + " - Novo Salário: R$ " + decimalFormat.format(funcionario.getSalario())
        ));
        System.out.println("--------------------------------------------------");

        System.out.println("Agrupando Funcionários por função...");
        Map<String, List<Funcionario>> funcionariosPorFuncao = app.funcionariosPorFuncao();
        System.out.println("Funcionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> System.out.println(
                    "  Nome: " + funcionario.getNome() +
                            " | Data de Nascimento: " + funcionario.getDataNascimento().format(dateFormatter) +
                            " | Salário: R$ " + decimalFormat.format(funcionario.getSalario())
            ));
        });
        System.out.println("--------------------------------------------------");

        System.out.println("Filtrando funcionários com aniversário em Outubro e Dezembro...");
        List<Funcionario> funcionariosAniversario = app.funcionariosPorAniversario(10,12);
        imprimirListaFuncionarios(funcionariosAniversario, "Funcionários com aniversário em Outubro e Dezembro");

        System.out.println("Descobrindo funcionário com a maior idade...");
        Funcionario funcionarioMaisVelho = app.funcionarioComMaiorIdade();

        if (funcionarioMaisVelho != null) {
            int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Funcionário com maior idade: " + funcionarioMaisVelho.getNome() + " - " + idade + " anos.");
        }
        System.out.println("--------------------------------------------------");

        System.out.println("Organizando funcionários em ordem alfabética...");
        funcionarios = app.funcionarioOrdemAlfabetica();
        imprimirListaFuncionarios(funcionarios, "Funcionários em ordem alfabética");

        System.out.println("Calculando total dos salários dos dos funcionários");
        BigDecimal totalSalarios = app.totalSalarioFuncionarios();
        System.out.println("Total dos salários dos funcionários: R$ " + decimalFormat.format(totalSalarios));
        System.out.println("--------------------------------------------------");

        System.out.println("Verificando a quantidade de salários mínimos por funcionário...");
        System.out.println("Quantidade de salários mínimos por funcionário:");
        app.salarioMinimoProFuncionario();

    }
}