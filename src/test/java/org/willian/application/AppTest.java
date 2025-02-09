package org.willian.application;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.willian.domain.entities.Funcionario;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private String getSampleJson() {
        return "[\n" +
                "  {\n" +
                "    \"nome\": \"João\",\n" +
                "    \"salario\": 2000.00,\n" +
                "    \"dataNascimento\": \"1980-05-12\",\n" +
                "    \"funcao\": \"Analista\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"nome\": \"Maria\",\n" +
                "    \"salario\": 3000.00,\n" +
                "    \"dataNascimento\": \"1975-09-23\",\n" +
                "    \"funcao\": \"Gerente\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"nome\": \"Caio\",\n" +
                "    \"salario\": 2500.00,\n" +
                "    \"dataNascimento\": \"1990-12-01\",\n" +
                "    \"funcao\": \"Analista\"\n" +
                "  }\n" +
                "]";
    }

    @Test
    @DisplayName("Teste dando um arquivo json deve retornar uma lista de Funcionarios")
    public void testCriarListaFuncionarios() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        List<Funcionario> funcionarios = app.criarListaFuncionarios(inputStream);

        assertNotNull(funcionarios, "A lista de funcionários não pode ser nula.");
        assertEquals(3, funcionarios.size(), "A lista deve conter 3 funcionários.");

        Funcionario f1 = funcionarios.get(0);
        assertEquals("João", f1.getNome());
        assertEquals(new BigDecimal("2000.00"), f1.getSalario());
        assertEquals(LocalDate.of(1980, 5, 12), f1.getDataNascimento());
        assertEquals("Analista", f1.getFuncao());
    }

    @Test
    @DisplayName("Teste RemoverFuncionarios dando um nome deve remover o funcionarios da lsita de funcionarios")
    public  void testRemoverFuncionarios() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        List<Funcionario> funcionarioList = app.removerFuncionario("João");

        assertEquals(2, funcionarioList.size(), "Após remoção, a lista deve conter 2 funcinários");
        assertFalse(funcionarioList.stream().anyMatch(funcionario -> funcionario.getNome().equalsIgnoreCase("João")),
                "A lista não deve conter o funcionário removido");
    }

    @Test
    @DisplayName("Teste AdicionarAumentoDeSalario deve retornar a lista dos funcionários com o salario aumentado")
    public void testAdicionarAumentoDeSalario() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        List<Funcionario> funcionariosAtualizados = app.adicionarAumentoDeSalario(1.10);

        for (Funcionario funcionario : funcionariosAtualizados) {
            BigDecimal salarioOriginal;
            switch (funcionario.getNome()) {
                case "João":
                    salarioOriginal = new BigDecimal("2000.00");
                    break;
                case "Maria":
                    salarioOriginal = new BigDecimal("3000.00");
                    break;
                case "Caio":
                    salarioOriginal = new BigDecimal("2500.00");
                    break;
                default:
                    fail("Funcionário inesperado: " + funcionario.getNome());
                    return;
            }
            BigDecimal salarioEsperado = salarioOriginal
                    .multiply(new BigDecimal("1.10"))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            assertEquals(salarioEsperado, funcionario.getSalario(),
                    "O salário do funcionário " + funcionario.getNome() + " não foi atualizado corretamente.");
        }
    }

    @Test
    @DisplayName("Teste funcionariosPorFuncao deve retornar uma lista de funcionarios organizadas por função")
    public void testFuncionariosPorFuncao() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        Map<String, List<Funcionario>> agrupados = app.funcionariosPorFuncao();

        // Verifica se existem duas funções (Analista e Gerente)
        assertEquals(2, agrupados.size(), "Devem existir 2 funções agrupadas.");
        assertTrue(agrupados.containsKey("Analista"));
        assertTrue(agrupados.containsKey("Gerente"));
        assertEquals(2, agrupados.get("Analista").size(), "Devem existir 2 Analistas.");
        assertEquals(1, agrupados.get("Gerente").size(), "Deve existir 1 Gerente.");
    }

    @Test
    @DisplayName("Teste funcionariosPorAniversario dando dois meses deve retornar uma lista de funcionarios filtradas por data de aniversário")
    public void testFuncionariosPorAniversario() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        // Supondo que queremos funcionários que fazem aniversário em maio (5) ou setembro (9)
        List<Funcionario> aniversariantes = app.funcionariosPorAniversario(5, 9);
        assertEquals(2, aniversariantes.size(), "Devem existir 2 funcionários com aniversário nos meses informados.");

        List<String> nomes = aniversariantes.stream().map(Funcionario::getNome).toList();
        assertTrue(nomes.contains("João"));
        assertTrue(nomes.contains("Maria"));
    }

    @Test
    @DisplayName("Test funcionarioComMaiorIdade deve retornar o funcionário mais velho funcionario")
    public void testFuncionarioComMaiorIdade() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        Funcionario funcionarioMaisVelho = app.funcionarioComMaiorIdade();
        assertNotNull(funcionarioMaisVelho, "O funcionário mais velho não pode ser nulo.");
        assertEquals("Maria", funcionarioMaisVelho.getNome(),
                "O funcionário com maior idade não é o esperado.");
    }

    @Test
    @DisplayName("Teste funcionarioOrdemAlfabetica deve retornar uma lista de funcionário em ordem alfabética")
    public void testFuncionarioOrdemAlfabetica() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        List<Funcionario> funcionariosOrdenados = app.funcionarioOrdemAlfabetica();
        assertEquals(3, funcionariosOrdenados.size(), "Devem existir 3 funcionários na lista ordenada.");

        assertEquals("Caio", funcionariosOrdenados.get(0).getNome());
        assertEquals("João", funcionariosOrdenados.get(1).getNome());
        assertEquals("Maria", funcionariosOrdenados.get(2).getNome());
    }

    @Test
    @DisplayName("Teste totalSalarioFuncionarios deve retornar o somatorio de todos os salários")
    public void testTotalSalarioFuncionarios() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        BigDecimal totalSalarios = app.totalSalarioFuncionarios();
        assertEquals(new BigDecimal("7500.00"), totalSalarios,
                "O total dos salários não é o esperado.");
    }

    @Test
    @DisplayName("Teste salarioMinimoProFuncionario não deve causar exceções")
    public void testSalarioMinimoProFuncionario() {
        App app = new App();
        InputStream inputStream = new ByteArrayInputStream(getSampleJson().getBytes());
        app.criarListaFuncionarios(inputStream);

        assertDoesNotThrow(app::salarioMinimoProFuncionario,
                "O método salarioMinimoProFuncionario() deve ser executado sem lançar exceções.");
    }

}
