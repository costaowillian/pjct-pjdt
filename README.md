# Instruções de Execução do Projeto

## Pré-requisitos

Antes de rodar o projeto, verifique se você tem os seguintes pré-requisitos:

- **Java JDK 21** ou superior instalado em sua máquina.
- **IntelliJ IDEA** instalado.

## Instalação

### Passo 1: Clone o Repositório

Clone o repositório para sua máquina local com o seguinte comando:

```bash git clone https://github.com/costaowillian/pjct-pjdt.git```

## Passo 2: Abra o Projeto no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. No IntelliJ, vá para **File > New > Project from Version Control**.
3. Selecione **Git** e cole a URL do repositório: `https://github.com/costaowillian/pjct-pjdt.git`.
4. Escolha o diretório onde deseja salvar o projeto e clique em **Clone**.

## Passo 3: Importar o Projeto

1. Caso o IntelliJ não detecte automaticamente o tipo de projeto, vá para **File > Open** e selecione a pasta onde você clonou o repositório.
2. O IntelliJ irá detectar que se trata de um projeto Java e configurará automaticamente as dependências.

## Passo 4: Verifique o JDK

1. No IntelliJ, vá para **File > Project Structure** (ou pressione **Ctrl+Alt+Shift+S**).
2. No painel esquerdo, selecione **Project** e em **Project SDK**, certifique-se de que o **JDK 21** ou superior está selecionado.
3. Caso o JDK 21 não esteja listado, clique em **Add SDK** e selecione o caminho para a instalação do JDK 21 em sua máquina.

## Passo 5: Execute o Projeto

1. No IntelliJ, vá até a barra de ferramentas superior e selecione a configuração de execução.
2. Selecione a classe principal do projeto (por exemplo, `Main.java`).
3. Clique no ícone de **play** (ou pressione **Shift+F10**) para executar o projeto.

## Passo 6: Executar Testes

1. No IntelliJ, navegue até a pasta `src/test/java` no painel de projetos.
2. Localize a classe de testes que deseja executar (por exemplo, `MainTest.java`).
3. Clique com o botão direito na classe de teste e selecione **Run** (ou use o atalho **Ctrl+Shift+F10**).
4. Verifique os resultados dos testes no painel **Run** para garantir que todos os testes foram aprovados.
5. Caso algum teste falhe, revise o código e os testes para corrigir possíveis erros.
