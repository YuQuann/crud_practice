package controller;

import entity.Member;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import response.MemberResponse;
import service.MemberService;

import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberController {

    @Inject
    MemberService memberService;

    /***
     * 取得全部會員資料
     * @return Uni<List < Member>>
     */
    @GET
    @Path("/members")
    public Uni<List<Member>> getAll() {
        return memberService.getAll();
    }

    /***
     * 套用ResponseBase回傳Member物件測試
     * @param name 會員名稱
     * @return Uni<MemberResponse>
     */
    @GET
    @Path("/{name}")
    public Uni<MemberResponse> getByName(@PathParam("name") String name) {

        return memberService.getByName(name).onItem()
                .transform(member -> {
                    MemberResponse memberResponse = new MemberResponse();
                    if (member.isEmpty()) {
                        memberResponse.setMemberResponse(member)
                                .setCode(HttpResponseStatus.NOT_FOUND.code())
                                .setMsg("Fail")
                                .setSuccess(false)
                                .setStatus("回傳Member物件(空)");
                        return memberResponse;
                    }
                    memberResponse.setMemberResponse(member)
                            .setCode(HttpResponseStatus.OK.code())
                            .setMsg("Success")
                            .setSuccess(true)
                            .setStatus("回傳Member物件");
                    return memberResponse;
                });
    }

    /***
     * 新增會員，Service新增會員名稱判定是否重複，會回傳Message給前端判斷
     * @param member 會員物件
     * @return Uni<MemberResponse>
     */
    @POST
    @Path("/member")
    public Uni<MemberResponse> addMember(Member member) {

        return memberService.addMember(member).onItem()
                .transform((member1 -> {
                    MemberResponse memberResponse = new MemberResponse(member1);

                    if (member1.isEmpty()) {
                        memberResponse.setMsg("失敗,會員名稱重複");
                        memberResponse.setSuccess(false);
                        memberResponse.setCode(HttpResponseStatus.NOT_ACCEPTABLE.code());
                    } else {
                        memberResponse.setMsg("成功");
                        memberResponse.setSuccess(true);
                        memberResponse.setCode(HttpResponseStatus.OK.code());
                    }
                    return memberResponse;
                }));
    }

    /***
     * 更新會員的名稱，但設計內是找firstResult去改，所以並沒有準確的辨識度，僅供練習
     * @param name 原本的會員名稱
     * @param updateName 欲更新的會員名稱
     * @return Uni<MemberResponse>
     */
    @PUT
    @Path("/member")
    public Uni<MemberResponse> updateMember(@QueryParam("name") String name,
                                            @QueryParam("updateName") String updateName) {

        return memberService.updateMember(name, updateName).onItem()
                .transform(member -> {
                    MemberResponse memberResponse = new MemberResponse();
                    if (member != null) {
                        memberResponse.setMemberResponse(member)
                                .setCode(HttpResponseStatus.OK.code())
                                .setSuccess(true)
                                .setMsg("修改成功");
                    } else {
                        memberResponse.setMemberResponse(new Member())
                                .setCode(HttpResponseStatus.NOT_FOUND.code())
                                .setSuccess(false)
                                .setMsg("修改失敗");
                    }
                    return memberResponse;
                });
    }

    /***
     * 透過Path提供的會員名稱刪除會員
     * @param name 會員名稱
     * @return Uni<Void>
     */
    @DELETE
    @Path("/{name}")
    public Uni<Void> deleteMember(@PathParam("name") String name) {
        return memberService.deleteMember(name);
    }

}
