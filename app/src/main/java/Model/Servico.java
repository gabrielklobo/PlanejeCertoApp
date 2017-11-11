package Model;

/**
 * Created by gaplo on 11/11/2017.
 */

public class Servico {
    private int servicoId;
    private String servicoNome;

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public String getServicoNome() {
        return servicoNome;
    }

    public void setServicoNome(String servicoNome) {
        this.servicoNome = servicoNome;
    }

    @Override
    public String toString() {
        return  servicoNome;
    }
}
