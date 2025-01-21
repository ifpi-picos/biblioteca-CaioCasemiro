package servico;

public class NotificacaoEmail implements Notificacao {
    @Override
    public void enviarNotificacao(String mensagem) {
        System.out.println("Notificação por e-mail: " + mensagem);
    }
}