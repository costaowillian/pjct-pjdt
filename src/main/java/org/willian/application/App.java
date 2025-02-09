package org.willian.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.willian.domain.entities.Funcionario;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe responsavel por gerenciar operacoes relacionadas aos funcionarios.
 *
 * Esta classe permite:
 *  - Criar uma lista de funcionarios a partir de um JSON.
 *  - Remover um funcionario pelo nome.
 *  - Aplicar aumento de salario aos funcionarios.
 *  - Agrupar funcionarios por funcao.
 *  - Filtrar funcionarios por mes de aniversario.
 *  - Identificar o funcionario com maior idade.
 *  - Ordenar funcionarios em ordem alfabetica.
 *  - Calcular o total dos salarios dos funcionarios.
 *  - Exibir a quantidade de salarios minimos que cada funcionario recebe.
 */

public class App {

    List<Funcionario> funcionarios;

    public App() {}

    /**
     * Cria uma lista de funcionarios a partir de um JSON fornecido via InputStream.
     *
     * @param jsonInputStream o InputStream contendo o JSON com a lista de funcionarios.
     * @return a lista de funcionarios criada; se ocorrer erro na leitura, retorna a lista atual (possivelmente nula).
     */
    public List<Funcionario> criarListaFuncionarios(InputStream jsonInputStream) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        try {
            List<Funcionario> funcionariosList = objectMapper.readValue(
                    jsonInputStream,
                    new TypeReference<List<Funcionario>>() {}
            );

            this.setFuncionarios(funcionariosList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }

    /**
     * Define a lista de funcionarios.
     *
     * @param funcionarios a lista de funcionarios a ser definida.
     */
    private void setFuncionarios (List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    /**
     * Remove da lista o funcionario cujo nome corresponda ao informado (ignora maiusculas e minusculas).
     *
     * @param nome o nome do funcionario a ser removido.
     * @return a lista de funcionarios atualizada.
     */
    public List<Funcionario> removerFuncionario (String nome) {
        this.funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
        return this.funcionarios;
    }

    /**
     * Aplica um aumento no salario de todos os funcionarios.
     *
     * O aumento e calculado multiplicando o salario atual pelo valor informado.
     * Por exemplo, para um aumento de 10% deve-se passar 1.10 como parametro.
     *
     * @param valor o fator de multiplicacao para o aumento (ex.: 1.10 para 10%).
     * @return a lista de funcionarios com os salarios atualizados.
     */
    public List<Funcionario> adicionarAumentoDeSalario (Double valor) {
        this.funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal(valor));
            funcionario.setSalario(novoSalario.setScale(2, RoundingMode.HALF_UP));
        });

        return this.funcionarios;
    }

    /**
     * Agrupa os funcionarios por sua funcao.
     *
     * @return um Map onde a chave e a funcao e o valor e a lista de funcionarios com aquela funcao.
     */
    public Map<String, List<Funcionario>> funcionariosPorFuncao () {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        return funcionariosPorFuncao;
    }

    /**
     * Filtra os funcionarios que fazem aniversario em um dos dois meses especificados.
     *
     * @param mesInicial o primeiro mes a ser considerado (valor entre 1 e 12).
     * @param mesFinal o segundo mes a ser considerado (valor entre 1 e 12).
     * @return uma lista de funcionarios que fazem aniversario em mesInicial ou mesFinal.
     */
    public List<Funcionario> funcionariosPorAniversario(int mesInicial, int mesFinal) {
        List<Funcionario> funcionariosAniversario = funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == mesInicial || mes == mesFinal;
                }).toList();
        return funcionariosAniversario;
    }

    /**
     * Retorna o funcionario com maior idade, com base na data de nascimento.
     *
     * @return o funcionario mais velho; se a lista estiver vazia, retorna null.
     */
    public Funcionario funcionarioComMaiorIdade(){
        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        return funcionarioMaisVelho;
    }

    /**
     * Retorna uma lista de funcionarios ordenada alfabeticamente pelo nome.
     *
     * @return a lista de funcionarios ordenada em ordem alfabetica.
     */
    public List<Funcionario> funcionarioOrdemAlfabetica() {
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        return funcionariosOrdenados;
    }

    /**
     * Calcula o total dos salarios de todos os funcionarios.
     *
     * @return o valor total dos salarios.
     */
    public BigDecimal totalSalarioFuncionarios () {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSalarios;
    }

    /**
     * Exibe no console a quantidade de salarios minimos que cada funcionario recebe.
     *
     * Considera o valor do salario minimo fixo em R$1212.00.
     * Para cada funcionario, e calculado o quociente entre o seu salario e o salario minimo,
     * sendo o resultado arredondado para 2 casas decimais.
     */
    public void salarioMinimoProFuncionario() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal qtdSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Nome: " + funcionario.getNome() + " - " + qtdSalariosMinimos + " salários mínimos.");
        });
    }
}
