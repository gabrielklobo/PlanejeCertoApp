package Model;

/**
 * Created by gaplo on 11/11/2017.
 */

public class Unidade {
    private int unidadeId;
    private String unidadeNome;

    public int getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(int unidadeId) {
        this.unidadeId = unidadeId;
    }

    public String getUnidadeNome() {
        return unidadeNome;
    }

    public void setUnidadeNome(String unidadeNome) {
        this.unidadeNome = unidadeNome;
    }

    @Override
    public String toString() {
        return  unidadeNome;
    }
}
