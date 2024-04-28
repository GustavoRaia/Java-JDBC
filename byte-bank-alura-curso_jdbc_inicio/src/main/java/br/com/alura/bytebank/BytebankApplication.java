package br.com.alura.bytebank;

import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.Conta;
import br.com.alura.bytebank.domain.conta.ContaService;
import br.com.alura.bytebank.domain.conta.DadosAberturaConta;

import java.util.Scanner;

public class BytebankApplication {

    private static ContaService service = new ContaService();
    private static Scanner teclado = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var opcao = exibirMenu();
        while (opcao != 0) {
            try {
                switch (opcao) {
                    case 1:
                        listarContas();
                        break;
                    case 2:
                        abrirConta();
                        break;
                    case 3:
                        encerrarConta();
                        break;
                    case 4:
                        consultarSaldo();
                        break;
                    case 5:
                        realizarSaque();
                        break;
                    case 6:
                        realizarDeposito();
                        break;
                    case 7 :
                        realizarTransferencia();
                        break;
                    case 8:
                        buscarContaPorNumero();
                        break;
                    case 9:
                        listarContasInativas();
                }
            } catch (RegraDeNegocioException e) {
                System.out.println("Erro: " +e.getMessage());
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
                teclado.next();
            }
            opcao = exibirMenu();
        }

        System.out.println("Finalizando a aplicação.");
    }

    private static int exibirMenu() {
        System.out.println("""
                BYTEBANK - ESCOLHA UMA OPÇÃO:
                1 - Listar contas abertas
                2 - Abertura de conta
                3 - Encerramento de conta
                4 - Consultar saldo de uma conta
                5 - Realizar saque em uma conta
                6 - Realizar depósito em uma conta
                7 - Realizar uma transferência
                8 - Buscar uma conta pelo número
                9 - Listar as contas inativas
                0 - Sair
                """);
        return teclado.nextInt();
    }

    // 1 - Listar Contas Abertas
    private static void listarContas() {
        System.out.println("Contas cadastradas:");
        var contas = service.listarContasAbertas();
        contas.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    // 2 - Abertura de Conta
    private static void abrirConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o nome do cliente:");
        var nome = teclado.next();

        System.out.println("Digite o cpf do cliente:");
        var cpf = teclado.next();

        System.out.println("Digite o email do cliente:");
        var email = teclado.next();

        try {
            service.abrir(new DadosAberturaConta(numeroDaConta, new DadosCadastroCliente(nome, cpf, email)));
            System.out.println("Conta aberta com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao salvar conta: " + e);
        }

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    // 3 - Encerramento de Conta (Safe Delete)
    private static void encerrarConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        service.encerrar(numeroDaConta);

        System.out.println("Conta encerrada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    // 4 - Consultar Saldo da Conta
    private static void consultarSaldo() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();
        var saldo = service.consultarSaldo(numeroDaConta);
        System.out.println("Saldo da conta: " +saldo);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    // 5 - Realizar Saque da Conta
    private static void realizarSaque() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o valor do saque:");
        var valor = teclado.nextBigDecimal();

        service.realizarSaque(numeroDaConta, valor);

        System.out.println("Saque realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");

        teclado.next();
    }

    // 6 - Realizar Depósito na Conta
    private static void realizarDeposito() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o valor do depósito:");
        var valor = teclado.nextBigDecimal();

        service.realizarDeposito(numeroDaConta, valor);

        System.out.println("Depósito realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");

        teclado.next();
    }

    // 7 - Realizar Transferência entre Contas
    private static void realizarTransferencia() {
        System.out.println("Digite o número da conta de origem:");
        var numeroDaContaOrigem = teclado.nextInt();

        System.out.println("Digite o número da conta de destino:");
        var numeroDaContaDestino = teclado.nextInt();

        System.out.println("Digite o valor da transferência:");
        var valor = teclado.nextBigDecimal();

        service.realizarTransferencia(numeroDaContaOrigem, numeroDaContaDestino, valor);

        System.out.println("Transferência realizada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");

        teclado.next();
    }

    // 8 - Buscar Conta através do Número
    public static void buscarContaPorNumero() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        Conta conta = service.buscarContaPorNumero(numeroDaConta);

        System.out.println("Número: " + conta.getNumero());
        System.out.println("Saldo: " + conta.getSaldo());
        System.out.println("Titular: " + conta.getTitular().getNome());
        System.out.println("CPF: " + conta.getTitular().getCpf());
        System.out.println("Email: " + conta.getTitular().getEmail());

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");

        teclado.next();
    }

    // 9 - Listar Contas Inativas
    private static void listarContasInativas() {
        System.out.println("Contas cadastradas:");
        var contas = service.listarContasFechadas();
        contas.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }


}
