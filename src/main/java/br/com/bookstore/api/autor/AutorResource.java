package br.com.bookstore.api.autor;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status;

/**
 *
 * @author aula
 */
@Path("autores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutorResource {

    static List<Autor> autores = new ArrayList<>(
        Arrays.asList(
            new Autor(1, "David Cockford", LocalDate.of(1959, Month.MARCH, 1), Genero.MASCULINO),
            new Autor(2, "JK Rowling", LocalDate.of(1953, Month.MARCH, 1), Genero.FEMININO)
        )
    );

    public AutorResource() {}

    @GET
    public List<Autor> getAutores() {
        return autores;
    }

    @GET
    @Path("{id}")
    public Response getAutor(@PathParam("id") int id) {
        Autor autor = findAutor(id);
        if (autor == null) {
            return autorNotFoundResponse();
        }
        return Response.ok(autor).build();
    }

    @POST
    public Response addAutor(Autor autor) {
        int ultimoId = autores.get(autores.size()-1).getId();
        autor.setId(++ultimoId);
        autores.add(autor);
        return Response.status(Status.CREATED).entity(autor).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        Autor autor = findAutor(id);
        if(autor == null) {
            return autorNotFoundResponse();
        } 
        autores.remove(autor);
//        return Response.status(Status.NO_CONTENT).build();
        return Response.noContent().build();
    }
    
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") int id, Autor autorAtualizado) {
        Autor autor = findAutor(id);
        if(autor == null) {
            return autorNotFoundResponse();
        }
        autor.setNome(autorAtualizado.getNome());
        autor.setDataNascimento(autorAtualizado.getDataNascimento());
        autor.setGenero(autorAtualizado.getGenero());
        return Response.ok(autor).build();
    }
    
    public Autor findAutor(int id) {
        return autores
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);        
    }
    
    public Response autorNotFoundResponse() {      
//            throw new NotFoundException("Autor não encontrado");
//            throw new  WebApplicationException("Autor não encontrado", Status.NOT_FOUND);;
        return Response
                    .status(Status.NOT_FOUND)
                    .entity("Autor não encontrado para exclusão")
                    .build();
    }
}