package com.codestates.mainproject.oneyearfourcut.domain.vote.repository;

import com.codestates.mainproject.oneyearfourcut.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
