package premium_pipe.service.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.StaticTypeEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.model.StaticTypeRequest;
import premium_pipe.repository.StaticTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticTypeAdminService {
    private final StaticTypeRepository staticTypeRepository;


    public void create(StaticTypeRequest request) {
        StaticTypeEntity entity = StaticTypeEntity.builder()
                .name(request.getName())
                .build();
        staticTypeRepository.save(entity);
    }
    public StaticTypeEntity getEntity(final Long id){
        return staticTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Type not found"));
    }

    public void update(final Long id, final StaticTypeRequest request){
        StaticTypeEntity entity = getEntity(id);
        entity.setName(request.getName());
        staticTypeRepository.save(entity);
    }
    public void delete(final Long id){
        StaticTypeEntity entity = getEntity(id);
        staticTypeRepository.delete(entity);
    }

    public Page<StaticTypeEntity> getList(Pageable pageable){
        return staticTypeRepository.findAll(pageable);
    }

    public List<StaticTypeEntity> getTypes() {
        return (List<StaticTypeEntity>) staticTypeRepository.findAll();
    }
}
