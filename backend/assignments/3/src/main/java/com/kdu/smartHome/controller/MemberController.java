package com.kdu.smartHome.controller;

import com.kdu.smartHome.dto.request.AddMemberRequest;
import com.kdu.smartHome.dto.request.TransferOwnershipRequest;
import com.kdu.smartHome.dto.response.MessageResponse;
import com.kdu.smartHome.service.CurrentUserService;
import com.kdu.smartHome.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for house members and ownership.
 */

@RestController
@RequestMapping("/api/v1/houses/{plotId}")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CurrentUserService currentUserService;
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    /**
     * Adds a user to a house as a member.
     */
    @PostMapping("/members")
    public ResponseEntity<MessageResponse> addMember(@PathVariable Long plotId,
            @Valid @RequestBody AddMemberRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Add member {} to house {} by user {}", request.getUserId(), plotId, userId);
        memberService.addMember(plotId, request.getUserId(), userId);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Member added")
                .build());
    }

    /**
     * Transfers house ownership to another member.
     */
    @PostMapping("/transfer-ownership")
    public ResponseEntity<MessageResponse> transferOwnership(@PathVariable Long plotId,
            @Valid @RequestBody TransferOwnershipRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        log.info("Transfer ownership of house {} to {} by {}", plotId, request.getNewAdminUserId(), userId);
        memberService.transferOwnership(plotId, request.getNewAdminUserId(), userId);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Ownership transferred")
                .build());
    }
}
