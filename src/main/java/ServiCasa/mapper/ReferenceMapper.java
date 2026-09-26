package ServiCasa.mapper;

import ServiCasa.entity.Specialite;
import ServiCasa.entity.Ville;
import ServiCasa.repository.SpecialiteRepository;
import ServiCasa.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ReferenceMapper {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    public Ville toVille(String nom) {
        if (nom == null || nom.isBlank()) {
            return null;
        }
        String value = nom.trim();
        return villeRepository.findByNom(value)
                .orElseGet(() -> {
                    Ville ville = new Ville();
                    ville.setNom(value);
                    return villeRepository.save(ville);
                });
    }

    public String toVilleNom(Ville ville) {
        return ville == null ? null : ville.getNom();
    }

    public Specialite toSpecialite(String nom) {
        if (nom == null || nom.isBlank()) {
            return null;
        }
        String value = nom.trim();
        return specialiteRepository.findByNom(value)
                .orElseGet(() -> {
                    Specialite specialite = new Specialite();
                    specialite.setNom(value);
                    return specialiteRepository.save(specialite);
                });
    }

    public String toSpecialiteNom(Specialite specialite) {
        return specialite == null ? null : specialite.getNom();
    }
}
